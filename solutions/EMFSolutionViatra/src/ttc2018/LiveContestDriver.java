package ttc2018;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import Changes.ChangesFactory;
import Changes.ChangesPackage;
import Changes.ModelChangeSet;
import SocialNetwork.SocialNetworkFactory;
import SocialNetwork.SocialNetworkPackage;
import SocialNetwork.SocialNetworkRoot;

public class LiveContestDriver {

	public static void main(String[] args) {
		try {
	        Initialize();
	        Load();
	        Initial();
	        for (int i = 1; i <= Sequences; i++)
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

    private static long stopwatch;

    private static Solution solution;

    private static Object loadFile(String path) throws IOException {
    	Resource mRes = repository.getResource(URI.createFileURI(new File(ChangePath + "/" + path).getCanonicalPath()), true);
    	return mRes.getContents().get(0);
    }

    static void Load() throws IOException
    {
    	stopwatch = System.nanoTime();
        solution.setSocialNetwork((SocialNetworkRoot)loadFile("initial.xmi"), repository);
        stopwatch = System.nanoTime() - stopwatch;
        Report(BenchmarkPhase.Load, -1, null);
    }

    static void Initialize() throws Exception
    {
    	stopwatch = System.nanoTime();

    	repository = new ResourceSetImpl();
		repository.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		repository.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		repository.getPackageRegistry().put(SocialNetworkPackage.eINSTANCE.getNsURI(), SocialNetworkPackage.eINSTANCE);
		repository.getPackageRegistry().put(ChangesPackage.eINSTANCE.getNsURI(), ChangesPackage.eINSTANCE);

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

    static void Initial()
    {
        stopwatch = System.nanoTime();
        String result = solution.Initial();
        stopwatch = System.nanoTime() - stopwatch;
        Report(BenchmarkPhase.Initial, -1, result);
    }

    static void Update(int iteration) throws IOException
    {
        ModelChangeSet changes = (ModelChangeSet)loadFile(String.format("change%02d.xmi", iteration));
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
        System.out.println(String.format("%s;%s;%s;%s;%s;%s;Time;%s", Tool, Query, ChangeSet, RunIndex, iterationStr, phase.toString(), Long.toString(stopwatch)));
        Runtime.getRuntime().gc();
        Runtime.getRuntime().gc();
        Runtime.getRuntime().gc();
        Runtime.getRuntime().gc();
        Runtime.getRuntime().gc();
        long memoryUsed = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
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
