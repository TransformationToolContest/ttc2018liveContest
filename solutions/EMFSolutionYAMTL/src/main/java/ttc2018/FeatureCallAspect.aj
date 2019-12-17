package ttc2018;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.eclipse.emf.ecore.EObject;

import yamtl.core.YAMTLModule;

@Aspect
public class FeatureCallAspect {
	YAMTLModule module;

	// when initializing many-valued features, a get is performed
	// we need this variable to control that initilize is only applied 
	// at the top level (first get)
	private int getLevel = 0;
	private int colLevel = 0;
	
	
	// TO BE UPDATED
	@Pointcut("within(SocialNetwork.impl.*)")
	// END TO BE UPDATED 
	private void syntacticScope() {}
	

	
	// //////////////////////////////////////////////////////////////
	// DO NOT MODIFY BELOW
	// //////////////////////////////////////////////////////////////
	@Before("within(yamtl.core.YAMTLModule) && execution(void execute())")
	public void getModule(JoinPoint thisJoinPoint) {
		module = (YAMTLModule) thisJoinPoint.getThis(); 
	}

	@Pointcut("(within(yamtl.core.YAMTLModule) && execution(* reduce(..)))")
	private void controlFlowReduceScope() {}

	@Pointcut("((within(yamtl.core.YAMTLModule) && execution(* findMatchesAndSchedule(..))))")
	private void controlFlowMatchingScope() {}

	@Pointcut("((within(yamtl.core.YAMTLModule) && execution(* insertTupleMatch(..))))")
	private void controlFlowInsertTrafostepScope() {}

	@Pointcut("((within(yamtl.core.YAMTLModule) && execution(* evaluateHelper(..))))")
	private void controlFlowQueryEvaluationScope() {}

	@After("cflowbelow(controlFlowMatchingScope()) && !cflowbelow(controlFlowInsertTrafostepScope()) && syntacticScope() && target(org.eclipse.emf.ecore.EObject) && execution(* *..get* (..))") // get(* *)
	public void featureGetCallInMatching(JoinPoint thisJoinPoint) {
		EObject eObj = (EObject) thisJoinPoint.getTarget();
		String featureName = thisJoinPoint.getSignature().getName();
		featureName = decapitalize(featureName.substring(3, featureName.length()));
		
		module.featureGetCallInMatching(eObj, featureName);
	}
	
	@After("(cflowbelow(controlFlowReduceScope()) || cflowbelow(controlFlowQueryEvaluationScope())) && syntacticScope() && target(org.eclipse.emf.ecore.EObject) && execution(* *..get* (..))") 
	public void featureGetCallInReduce(JoinPoint thisJoinPoint) {
		EObject eObj = (EObject) thisJoinPoint.getTarget();
		String featureName = thisJoinPoint.getSignature().getName();
		featureName = decapitalize(featureName.substring(3, featureName.length()));
		
		module.featureGetCallInReduce(eObj, featureName);
	}
	
	
	
	@After("(cflowbelow(controlFlowReduceScope()) ) && syntacticScope() && target(org.eclipse.emf.ecore.EObject) && execution(* *..set* (..))") 
	public void featureSetCallInReduce(JoinPoint thisJoinPoint) {
		EObject eObj = (EObject) thisJoinPoint.getTarget();
		String featureName = thisJoinPoint.getSignature().getName();
		featureName = decapitalize(featureName.substring(3, featureName.length()));
		
		module.featureGetCallInReduce(eObj, featureName);
	}
	
	@After("within(java.util.*) && (cflowbelow(controlFlowReduceScope()) ) && syntacticScope() && target(java.util.Collection) "
			+ "&& ("
				+ "call(* java.util.Collection.add(..)) || "
				+ "call(* java.util.Collection.addAll(..)) ||"
				+ "call(* java.util.Collection.remove(..)) ||"
				+ "call(* java.util.Collection.removeAll(..)) ||"
				+ "call(* java.util.Collection.clear(..)) ||"
				+ "call(* java.util.Collection.retainAll(..))"
			+ ")") 
	public void collectionModification(JoinPoint thisJoinPoint) {
		if (colLevel==0) {
			colLevel++;
			
			if (module != null) {
				module.collectionModification(thisJoinPoint);
			}
			
			colLevel--;
		}
	}
	
	
	public static String getFeatureName(String featureName) {
		if (featureName.startsWith("set"))
			return featureName.substring(3, featureName.length());
		else // unset
			return featureName.substring(5, featureName.length());
	}
	public static String decapitalize(String string) {
	    if (string == null || string.length() == 0) {
	        return string;
	    }
	    char c[] = string.toCharArray();
	    c[0] = Character.toLowerCase(c[0]);
	    return new String(c);
	}
}
