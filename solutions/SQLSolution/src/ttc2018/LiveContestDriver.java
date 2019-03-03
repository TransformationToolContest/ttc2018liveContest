package ttc2018;

import Changes.ChangesPackage;
import Changes.ModelChangeSet;
import SocialNetwork.SocialNetworkPackage;
import SocialNetwork.SocialNetworkRoot;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import java.io.File;
import java.io.IOException;

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

    private static ResourceSet repository;

    private static String ChangePath;
    private static String RunIndex;
    private static int Sequences;
    private static String Tool;
    private static String ChangeSet;
    private static String Query;
    private static SolutionModes Mode;

    static final boolean ShowScoresForValidation = "1".equals(System.getenv("SHOW_SCORES"));
    static final boolean ShowRunningTime = !"1".equals(System.getenv("HIDE_RUNTIME"));

    private static long stopwatch;

    private static Solution solution;

    private static Object loadFile(String path) throws IOException {
    	Resource mRes = repository.getResource(URI.createFileURI(new File(ChangePath + "/" + path).getCanonicalPath()), true);
    	return mRes.getContents().get(0);
    }

    static void Initialize() throws Exception
    {
    	stopwatch = System.nanoTime();

    	if (USE_CHANGES_XMI) {
            repository = new ResourceSetImpl();
            repository.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
            repository.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
            repository.getPackageRegistry().put(SocialNetworkPackage.eINSTANCE.getNsURI(), SocialNetworkPackage.eINSTANCE);
            repository.getPackageRegistry().put(ChangesPackage.eINSTANCE.getNsURI(), ChangesPackage.eINSTANCE);
        }

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

    static void Load() throws IOException, InterruptedException
    {
        stopwatch = System.nanoTime();
        if (USE_CHANGES_XMI) {
            solution.setSocialNetwork((SocialNetworkRoot) loadFile("initial.xmi"), repository);
        }
        solution.loadData();
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
        ModelChangeSet changes = (ModelChangeSet)loadFile(String.format("change%02d.xmi", iteration));
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
