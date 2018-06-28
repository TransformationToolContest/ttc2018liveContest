package ttc2018;

import SocialNetwork.SocialNetworkPackage
import SocialNetwork.SocialNetworkRoot
import java.sql.Timestamp
import org.eclipse.emf.ecore.resource.ResourceSet

public class LiveContestDriver {

	def public static void main(String[] args) {
		try {
	        Initialize();
	        Load();
	        Initial();
	        for (var i = 1; i <= Sequences; i++)
	        {
	            Update(i);
	        }	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
    private static ResourceSet repository;

    private static String ChangePath;
    private static String RunIndex;
    private static int Sequences;
    private static String Tool;
    private static String ChangeSet;
    private static String Query;

    var static long stopwatch;

    private static Solution solution;

    def private static Object loadFile(String path) {
//    	val Resource mRes = repository.getResource(URI.createFileURI(ChangePath + "/" + path), true);
		val modelPath = '''«ChangePath»/«path»'''
		println("model path: " + modelPath)
		solution.xform.loadInputModels(#{'sn' -> modelPath})
		println("loaded")
		val mRes = solution.xform.getModelResource('sn')
    	return mRes.getContents().get(0);
    }

    def static void Load()
    {
    	stopwatch = System.nanoTime();
        solution.setSocialNetwork(loadFile("initial.xmi") as SocialNetworkRoot);
        
        for (var iteration = 1; iteration <= Sequences; iteration++)
        {
        	val deltaName = '''change«iteration»'''
        	val deltaPath = '''«ChangePath»/change«iteration».documented.xmi'''
            solution.xform.loadDelta('sn', deltaName, deltaPath, new Timestamp(System.nanoTime()))
        }	
        
        stopwatch = System.nanoTime() - stopwatch;
        Report(BenchmarkPhase.Load, -1, null);
    }

    def static void Initialize() throws Exception
    {
    	stopwatch = System.nanoTime();

		SocialNetworkPackage.eINSTANCE.eClass()
		
//        println('System.getenv("ChangePath")= ' + System.getenv("ChangePath"))
        ChangePath = System.getenv("ChangePath");
        RunIndex = System.getenv("RunIndex");
//        println('System.getenv("Sequences")= ' + System.getenv("Sequences"))
        Sequences = Integer.parseInt(System.getenv("Sequences"));
        Tool = System.getenv("Tool");
//        println('System.getenv("ChangeSet")= ' + System.getenv("ChangeSet"))
        ChangeSet = System.getenv("ChangeSet");
        Query = System.getenv("Query").toUpperCase();
        if (Query.contentEquals("Q1"))
        {
            solution = new SolutionQ1();
        }
        else if (Query.contentEquals("Q2"))
        {
            solution = new SolutionQ2();
        }
        else
        {
            throw new Exception("Query is unknown");
        }

        stopwatch = System.nanoTime() - stopwatch;
        Report(BenchmarkPhase.Initialization, -1, null);
    }

    def static void Initial()
    {
        stopwatch = System.nanoTime();
        val String result = solution.Initial();
        stopwatch = System.nanoTime() - stopwatch;
        Report(BenchmarkPhase.Initial, -1, result);
    }

    def static void Update(int iteration)
    {
        val deltaName = '''change«iteration»'''
        stopwatch = System.nanoTime();
        val String result = solution.Update(deltaName);
        stopwatch = System.nanoTime() - stopwatch;
        Report(BenchmarkPhase.Update, iteration, result);
    }

    def static void Report(BenchmarkPhase phase, int iteration, String result)
    {
    	var String iterationStr;
    	if (iteration == -1) {
    		iterationStr = "0";
    	} else {
    		iterationStr = Integer.toString(iteration);
    	}
        System.out.println(String.format("%s;%s;%s;%s;%s;%s;Time;%s", Tool, Query, ChangeSet, RunIndex, iterationStr, phase.toString(), Long.toString(stopwatch)));
        Runtime.getRuntime().gc();
        Runtime.getRuntime().gc();
        Runtime.getRuntime().gc();
        Runtime.getRuntime().gc();
        Runtime.getRuntime().gc();
        val long memoryUsed = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.out.println(String.format("%s;%s;%s;%s;%s;%s;Memory;%s", Tool, Query, ChangeSet, RunIndex, iterationStr, phase.toString(), Long.toString(memoryUsed)));
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
}
