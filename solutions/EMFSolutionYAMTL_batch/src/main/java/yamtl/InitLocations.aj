package yamtl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

import yamtl.core.YAMTLModule;
import yamtl.core.Engine;

@Aspect
public class InitLocations {
	Engine engine;
	
	@Before("within(yamtl.core.YAMTLModule) && call(* loadInputModels(..))")
	public void getModule(JoinPoint thisJoinPoint) {
		engine = ( (YAMTLModule) thisJoinPoint.getThis() ).getEngine(); 
		engine.setInitLocationsWhenLoading(true);
	}
	
	@Before("cflowbelow(within(yamtl.registry.EmfMetamodelRegistry) && (execution(* loadModelEagerly(..)) || execution(* loadModel(..)))) && (within(org.eclipse.emf.ecore.resource.impl.ResourceImpl) && execution(* attachedHelper(..)))")
	public void attached(JoinPoint thisJoinPoint) {
		ResourceImpl res = (ResourceImpl) thisJoinPoint.getTarget();
		List<Object> objectList = Arrays.asList(thisJoinPoint.getArgs());
		for (Object obj : objectList) {
			EObject eObject = (EObject) obj;
			
			Map<String, EObject> map = res.getIntrinsicIDToEObjectMap();
		    if (map != null)
		    {
		      String id = EcoreUtil.getID(eObject);
		      if (id != null)
		      {
		    	  if (map.get(id) == null) {
			    	  // add to extent
			    	  String qName = eObject.eClass().getEPackage().getNsURI() + "/" + eObject.eClass().getName();
			    	  engine.getLocation().getTypeExtent().add(qName, eObject);
			    	  for (EClass superClass: eObject.eClass().getEAllSuperTypes()) {
			    		  String superClassName = superClass.getEPackage().getNsURI() + "/" + superClass.getName();
			    		  engine.getLocation().getTypeExtent().add(superClassName, eObject);
			    	  }
		    	  }
		      }
		    }
			
			
			
		}
	}

}
