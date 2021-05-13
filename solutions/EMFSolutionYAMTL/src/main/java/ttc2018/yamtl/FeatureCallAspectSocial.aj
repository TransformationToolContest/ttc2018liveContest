package ttc2018.yamtl;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.eclipse.emf.ecore.EObject;

import yamtl.core.YAMTLModule;
import yamtl.core.YAMTLModule.ExecutionMode;
import yamtl.core.YAMTLModule.IncrementalGranularity;

@Aspect
public class FeatureCallAspectSocial {
	YAMTLModule module;

	// TO BE UPDATED
	@Pointcut("within(SocialNetwork.impl.*)")
	// END TO BE UPDATED 
	private void syntacticScope() {}
	
	
	// //////////////////////////////////////////////////////////////
		// DO NOT MODIFY BELOW
		// //////////////////////////////////////////////////////////////
		@Before("within(yamtl.core.YAMTLModule) && execution(void executeMain())")
		public void getModule(JoinPoint thisJoinPoint) {
			module = (YAMTLModule) thisJoinPoint.getThis(); 
		}

		@Pointcut("within(yamtl.core.YAMTLModule) && execution(* findMatchesAndSchedule(..))")
		private void controlFlowMatchingScope() {}

		@Pointcut("within(yamtl.core.YAMTLModule) && execution(* insertTupleMatch(..))")
		private void controlFlowInsertTrafostepScope() {}

		@Pointcut("within(yamtl.core.YAMTLModule) && execution(* evaluateCallable(..))")
		private void controlFlowEvaluateCallableScope() {}

		@Pointcut("within(yamtl.core.YAMTLModule) && execution(* reduce(..))")
		private void controlFlowReduceScope() {}

		@Pointcut("within(yamtl.core.YAMTLModule) && execution(* applyOutputElementAction(..))")
		private void controlFlowOutputActionScope() {}

		
		/* 
		 * MATCHING 
		 */
		@After("cflowbelow(controlFlowMatchingScope()) && !cflowbelow(controlFlowEvaluateCallableScope()) && !cflowbelow(controlFlowInsertTrafostepScope()) && syntacticScope() && target(org.eclipse.emf.ecore.EObject) && execution(* *..get* (..))") // get(* *)
		public void featureGetCallInMatchingCommon(JoinPoint thisJoinPoint) {
			module.featureGetCall_matching_common(thisJoinPoint);
		}

		@After("cflowbelow(controlFlowEvaluateCallableScope()) && syntacticScope() && target(org.eclipse.emf.ecore.EObject) && execution(* *..get* (..))") // get(* *)
		public void featureGetCallInMatchingVariants(JoinPoint thisJoinPoint) {
			module.featureGetCall_matching_variants(thisJoinPoint);
		}	

		/* 
		 * REDUCE 
		 */
		@After("(cflowbelow(controlFlowReduceScope() && !cflowbelow(controlFlowOutputActionScope()) && !cflowbelow(controlFlowEvaluateCallableScope()))) && syntacticScope() && target(org.eclipse.emf.ecore.EObject) && execution(* *..get* (..))") 
		public void featureGetCallInReduce(JoinPoint thisJoinPoint) {
			module.featureGetCall_reduce_common(thisJoinPoint);
		}
		
		@After("cflowbelow(controlFlowReduceScope()) && (cflowbelow(controlFlowOutputActionScope()) || cflowbelow(controlFlowEvaluateCallableScope())) && syntacticScope() && target(org.eclipse.emf.ecore.EObject) && execution(* *..get* (..))") 
		public void featureGetCallInContextualElement(JoinPoint thisJoinPoint) {
			module.featureGetCall_reduce_variants(thisJoinPoint);
		}
}