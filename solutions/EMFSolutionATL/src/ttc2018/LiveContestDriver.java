package ttc2018;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import Changes.ChangesPackage;
import Changes.ModelChangeSet;
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

    private static Object loadFile(String path) {
    	Resource mRes;
		try {
			mRes = repository.getResource(URI.createFileURI(new File(ChangePath + "/" + path).getCanonicalPath()), true);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
    	return mRes.getContents().get(0);
    }

    static void Load()
    {
    	stopwatch = System.nanoTime();
        solution.setSocialNetwork((SocialNetworkRoot)loadFile("initial.xmi"));
        stopwatch = System.nanoTime() - stopwatch;
        Report(BenchmarkPhase.Load, -1, null);
    }

    static void Initialize() throws Exception
    {
    	stopwatch = System.nanoTime();

    	repository = new ResourceSetImpl();
		repository.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new Resource.Factory() {
			@Override
			public Resource createResource(URI uri) {
				XMIResourceImpl ret = new XMIResourceImpl(uri);
				ret.setIntrinsicIDToEObjectMap(new HashMap<>());
				ret.getDefaultLoadOptions().put(XMLResource.OPTION_DEFER_IDREF_RESOLUTION, true);
				return ret;
			}
		});
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
        	if(Tool.equals("EMFSolutionATL")) {
        		solution = new SolutionQ1();
        	} else if(Tool.equals("EMFSolutionATL-Incremental")){
        		solution = new SolutionQ1ATOL();
        	} else {
        		throw new IllegalArgumentException("Unknown tool: " + Tool);
        	}
        }
        else if (Query.contentEquals("Q2"))
        {
        	if(Tool.equals("EMFSolutionATL")) {
        		solution = new SolutionQ2();
        	} else if(Tool.equals("EMFSolutionATL-Incremental")){
        		solution = new SolutionQ2ATOL();
        	} else {
        		throw new IllegalArgumentException("Unknown tool: " + Tool);
        	}
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

    static void Update(int iteration)
    {
        ModelChangeSet changes = (ModelChangeSet)loadFile(String.format("change%02d.xmi", iteration));
        EcoreUtil.resolveAll(changes);
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
	if("true".equals(System.getenv("NoGC"))) {
		// nothing to do
	} else {
		Runtime.getRuntime().gc();
		Runtime.getRuntime().gc();
		Runtime.getRuntime().gc();
		Runtime.getRuntime().gc();
		Runtime.getRuntime().gc();
	}
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
