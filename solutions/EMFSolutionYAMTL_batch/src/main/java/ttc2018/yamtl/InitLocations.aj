package ttc2018.yamtl;

import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;

import yamtl.core.YAMTLModule;

@Aspect
public class InitLocations {
	YAMTLModule module;
	
	@Before("within(yamtl.core.YAMTLModule) && (call(* loadInputModels(..)) || call(* loadOutputModels(..)))")
	public void getModule(JoinPoint thisJoinPoint) {
		module = (YAMTLModule) thisJoinPoint.getThis(); 
		
		if (module.isDebug())
			System.out.println(">>>>>>>>>>> In aspect 'getModule'");
	}
	
	//@Before("cflowbelow(within(yamtl.core.YAMTLModule) && (execution(* loadInputModels(..)) || execution(* loadOutputModels(..)) || execution(* loadDelta(..)))) && (within(org.eclipse.emf.ecore.resource.impl.ResourceImpl) && execution(* attachedHelper(..)))")
	@Before("cflowbelow(within(yamtl.core.YAMTLModule) && (execution(* loadInputModels(..)) || execution(* loadDelta(..)))) && (within(org.eclipse.emf.ecore.resource.impl.ResourceImpl) && execution(* attachedHelper(..)))")
	public void attached(JoinPoint thisJoinPoint) {
		if (module.isDebug())
			System.out.println(">>>>>>>>>>> In aspect 'attached'");
		
		if (module.isInitLocationsWhenLoading()) {
			ResourceImpl res = (ResourceImpl) thisJoinPoint.getTarget();
			List<Object> objectList = Arrays.asList(thisJoinPoint.getArgs());
			module.initLocations(res, objectList);
		}
	}

}