package ttc2018;

import Changes.ModelChangeSet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LiveContestDriver {

	public static void main(String[] args) {
		try {
	        if (args.length == 0) {
	            Mode = SolutionModes.Incremental;
	        } else {
	            Mode = SolutionModes.valueOf(args[0]);
	        }
	        Initialize();
	        Load();
	        Initial();
	        for (int i = 1; i <= Sequences; i++)
	        {
	            if (USE_CHANGES_XMI) {
                    UpdateFromXMI(i);
                } else {
	                UpdateFromCSV(i);
                }
	        }
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private final static boolean REPORT_MEMORY_USAGE = false; // the SQL solution uses PostgreSQL, thus Java memory usage is not meaningful
    private final static boolean USE_CHANGES_XMI = false; // determines whether we use the XMI representation of changes file is to be used. Note: for the initial load, we always use the CSV representation

    private static String ChangePath;
    private static String RunIndex;
    private static int Sequences;
    private static String Tool;
    private static String ChangeSet;
    private static String Query;
    private static SolutionModes Mode;

    static final boolean ShowScoresForValidation = "1".equals(System.getenv("SHOW_SCORES"));
    static final boolean ShowRunningTime = !"1".equals(System.getenv("HIDE_RUNTIME"));
    static final boolean ShowDebugInfo = "1".equals(System.getenv("DEBUG"));

    private static long stopwatch;

    private static Solution solution;

    static void Initialize() throws Exception
    {
    	stopwatch = System.nanoTime();

        ChangePath = System.getenv("ChangePath");
        RunIndex = System.getenv("RunIndex");
        Sequences = Integer.parseInt(System.getenv("Sequences"));
        Tool = System.getenv("Tool");
        ChangeSet = System.getenv("ChangeSet");
        Query = System.getenv("Query").toUpperCase();
        if (Query.contentEquals("Q1"))
        {
            if (SolutionModes.Incremental.equals(Mode)) {
                solution = new SolutionQ1(ChangePath);
            } else {
                solution = new SolutionQ1Batch(ChangePath);
            }
        }
        else if (Query.contentEquals("Q2"))
        {
            if (SolutionModes.Incremental.equals(Mode)) {
                solution = new SolutionQ2(ChangePath);
            } else {
                solution = new SolutionQ2Batch(ChangePath);
            }
        }
        else
        {
            throw new Exception("Query is unknown");
        }

        stopwatch = System.nanoTime() - stopwatch;
        Report(BenchmarkPhase.Initialization, -1, null);
    }

    static void Load() throws IOException, InterruptedException, SQLException {
        stopwatch = System.nanoTime();
        if (USE_CHANGES_XMI) {
            solution.setSocialNetwork(ModelUtils.loadSocialNetworkFile(ChangePath));
        }

        Connection dbConnection = DriverManager.getConnection("jdbc:duckdb:");
        try (Statement stmt = dbConnection.createStatement()) {

            try (Stream<String> lines = Files.lines(Paths.get("load-scripts/schema.sql"))) {
                String schemaSql = lines.collect(Collectors.joining(System.lineSeparator()));
                stmt.execute(schemaSql);
            }
            try (Stream<String> lines = Files.lines(Paths.get("load-scripts/schema_constraints.sql"))) {
                String schemaSql = lines.collect(Collectors.joining(System.lineSeparator()));
                stmt.execute(schemaSql);
            }

        }

        solution.prepareStatements();
        stopwatch = System.nanoTime() - stopwatch;
        Report(BenchmarkPhase.Load, -1, null);
    }

    static void Initial()
    {
        stopwatch = System.nanoTime();
        String result = solution.Initial();
        stopwatch = System.nanoTime() - stopwatch;
        Report(BenchmarkPhase.Initial, -1, result);
    }

    static void UpdateFromXMI(int iteration) throws IOException
    {
        ModelChangeSet changes = ModelUtils.loadChangeSetFile(ChangePath, iteration);
        stopwatch = System.nanoTime();
        String result = solution.Update(changes);
        stopwatch = System.nanoTime() - stopwatch;
        Report(BenchmarkPhase.Update, iteration, result);
    }

    static void UpdateFromCSV(int iteration) throws IOException
    {
        File changes = ModelUtils.getChangesetCSVFile(ChangePath, iteration);
        stopwatch = System.nanoTime();
        String result = solution.Update(changes);
        stopwatch = System.nanoTime() - stopwatch;
        Report(BenchmarkPhase.Update, iteration, result);
    }

    static void Report(BenchmarkPhase phase, int iteration, String result)
    {
    	String iterationStr;
    	if (iteration == -1) {
    		iterationStr = "0";
    	} else {
    		iterationStr = Integer.toString(iteration);
    	}
    	if (ShowRunningTime) {
            System.out.println(String.format("%s;%s;%s;%s;%s;%s;Time;%s", Tool, Query, ChangeSet, RunIndex, iterationStr, phase.toString(), Long.toString(stopwatch)));
        }
        Runtime.getRuntime().gc();
        Runtime.getRuntime().gc();
        Runtime.getRuntime().gc();
        Runtime.getRuntime().gc();
        Runtime.getRuntime().gc();
        if (REPORT_MEMORY_USAGE) {
            long memoryUsed = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            System.out.println(String.format("%s;%s;%s;%s;%s;%s;Memory;%s", Tool, Query, ChangeSet, RunIndex, iterationStr, phase.toString(), Long.toString(memoryUsed)));
        }
        if (result != null)
        {
            System.out.println(String.format("%s;%s;%s;%s;%s;%s;Elements;%s", Tool, Query, ChangeSet, RunIndex, iterationStr, phase.toString(), result));
        }
    }

    enum BenchmarkPhase {
        Initialization,
        Load,
        Initial,
        Update
    }

    enum SolutionModes {
        Batch,
        Incremental
    }
}
