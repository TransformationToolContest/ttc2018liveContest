package yamtl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Pair;

import yamtl.core.ComputationStep;
import yamtl.core.MatchMap;
import yamtl.core.QueryStep;
import yamtl.core.YAMTLModule;

@Aspect
public class FeatureCallAspect {
	YAMTLModule module;

	
	
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

	@Pointcut("( (within(yamtl.utils.ReduceUtil) && execution(void reduce(yamtl.core.MatchMap))) )")
	private void controlFlowReduceScope() {}

	@Pointcut("((within(yamtl.utils.MatcherUtil) && execution(* findMatchesAndSchedule(..))) || (within(yamtl.core.Engine) && execution(* propagateDelta(..))))")
	private void controlFlowMatchingScope() {}

	@Pointcut("((within(yamtl.utils.MatcherUtil) && execution(* insertTupleMatch(..))))")
	private void controlFlowInsertTrafostepScope() {}

	@Pointcut("((within(yamtl.core.Engine) && execution(* evaluateHelper(yamtl.core.YAMTLHelper))))")
	private void controlFlowQueryEvaluationScope() {}

	@After("cflowbelow(controlFlowMatchingScope()) && !cflowbelow(controlFlowInsertTrafostepScope()) && syntacticScope() && target(org.eclipse.emf.ecore.EObject) && execution(* *..get* (..))") // get(* *)
	public void featureGetCallInMatching(JoinPoint thisJoinPoint) {
		EObject object = (EObject) thisJoinPoint.getTarget();
		
		if (object.eClass() instanceof EClass) {
			String featureName = thisJoinPoint.getSignature().getName();
			featureName = featureName.substring(3, featureName.length());
			
			//		module.getEngine().trackFeatureInSourcePattern(object, featureName);
			
			if ((module != null) && (object != null)) {
				if (this.module.debug) {
					System.out.print("	in filter: ");
					System.out.println("registering during MATCHING: " + object.eClass().getName() + "::" + featureName);
				}
				module.getEngine().getFeatureCallList().add(new Pair<EObject, String>(object, featureName));
			}
		}
	}

	@After("(cflowbelow(controlFlowReduceScope()) || cflowbelow(controlFlowQueryEvaluationScope())) && syntacticScope() && target(org.eclipse.emf.ecore.EObject) && execution(* *..get* (..))") 
	public void featureGetCallInReduce(JoinPoint thisJoinPoint) {
		EObject object = (EObject) thisJoinPoint.getTarget();
		String featureName = thisJoinPoint.getSignature().getName();
		featureName = featureName.substring(3, featureName.length());
		
		if ((module != null) && (object != null)) {
		 	ComputationStep step = module.getEngine().getCurrentTrafoStepStack().peek();
		 	
		 	if (this.module.debug) {
		 		System.out.print("	in aspect: ");
			 	if (step instanceof MatchMap) {
			 		System.out.println("	registering during REDUCING: " + object.eClass().getName() + "::" + featureName + " in trafo step " + ((MatchMap) step).getRule().getName());
			 	} else {
			 		System.out.println("	registering during REDUCING: " + object.eClass().getName() + "::" + featureName + " in helper " + ((QueryStep) step).getHelper().getName());
			 	}
		 	}

		 	List<ComputationStep>list = new ArrayList<ComputationStep>();
			list.add(step);
			Map<String, List<ComputationStep>> featureMap = module.getEngine().getFeatureCallToTrafoStep().get(object);
			if (featureMap == null) {
				featureMap = new LinkedHashMap<String,List<ComputationStep>>();
				featureMap.put(featureName, list);
				module.getEngine().getFeatureCallToTrafoStep().put(object, featureMap);
				
			} else {
				List<ComputationStep> trafoStepList = featureMap.get(featureName);
				if (trafoStepList == null) {
					featureMap.put(featureName, list);
				} else {
					trafoStepList.add(step);
				}
			}
			
		}
	}
}
