package ttc2018;

import Changes.ChangesPackage
import Changes.ModelChange
import Changes.ModelChangeSet
import SocialNetwork.SocialNetworkPackage
import SocialNetwork.SocialNetworkRoot
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.change.ChangeDescription

class LiveContestDriver {

	def static void main(String[] args) {
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
	
    static String ChangePath;
    static String RunIndex;
    static int Sequences;
    static String Tool;
    static String ChangeSet;
    static String Query;

    var static long stopwatch;

    static Solution solution;

    def private static Object loadFile(String path) {
		val modelPath = '''«ChangePath»/«path»'''
		solution.xform.loadInputModels(#{'sn' -> modelPath})
		val mRes = solution.xform.getModelResource('sn')
		
    	return mRes.getContents().get(0);
    }
    def private static Object loadDeltaFile(String path) {
		val modelPath = '''«ChangePath»/«path»'''
		val res = solution.xform.engine.registry.registry.loadModel(modelPath, true)
		res.contents.head
    }

    def static void Load()
    {
    	stopwatch = System.nanoTime();
        solution.setSocialNetwork(loadFile("initial.xmi") as SocialNetworkRoot);
        
        for (var iteration = 1; iteration <= Sequences; iteration++)
        {
	        val ModelChangeSet changes = loadDeltaFile(String.format("change%02d.xmi", iteration)) as ModelChangeSet
	        val EList<ModelChange> coll = changes.getChanges();
	        val deltaName = '''change«iteration»'''
			
			//
			solution.xform.recordSourceDelta('sn', deltaName, [
				for (ModelChange change : coll) {
					change.apply();
				}
				newHashMap
			])
			
			val delta = solution.xform.engine.registry.getDelta('sn', deltaName)
			(delta.deltaRes.contents.head as ChangeDescription).applyAndReverse()
        }
        
        for (var iteration = Sequences; iteration > 0; iteration--) {
        	val deltaName = '''change«iteration»'''
        	val delta = solution.xform.engine.registry.getDelta('sn', deltaName)
			(delta.deltaRes.contents.head as ChangeDescription).applyAndReverse()
        }
        
        stopwatch = System.nanoTime() - stopwatch;
        Report(BenchmarkPhase.Load, -1, null);
    }

    def static void Initialize() throws Exception
    {
    	stopwatch = System.nanoTime();

		SocialNetworkPackage.eINSTANCE.eClass()
		ChangesPackage.eINSTANCE.eClass()
		
        ChangePath = System.getenv("ChangePath");
        RunIndex = System.getenv("RunIndex");
        Sequences = Integer.parseInt(System.getenv("Sequences"));
        Tool = System.getenv("Tool");
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
        if (result !== null)
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
