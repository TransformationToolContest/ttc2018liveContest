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
		@Before("within(yamtl.core.YAMTLModule) && execution(void execute())")
		public void getModule(JoinPoint thisJoinPoint) {
			module = (YAMTLModule) thisJoinPoint.getThis(); 
		}

//		@Pointcut("within(yamtl.core.YAMTLModule) && execution(* applyLocalFilter(..))")
//		private void controlFlowLocalFilterScope() {}
		
		@Pointcut("within(yamtl.core.YAMTLModule) && execution(* findMatchesAndSchedule(..))")
		private void controlFlowMatchingScope() {}

		@Pointcut("within(yamtl.core.YAMTLModule) && execution(* insertTupleMatch(..))")
		private void controlFlowInsertTrafostepScope() {}

//		@Pointcut("within(yamtl.core.YAMTLModule) && execution(* evaluateHelper(..))")
//		private void controlFlowStaticAttributeHelperEvaluationScope() {}
		@Pointcut("within(yamtl.core.YAMTLModule) && execution(* evaluateCallable(..))")
		private void controlFlowEvaluateCallableScope() {}

		@Pointcut("within(yamtl.core.YAMTLModule) && execution(* reduce(..))")
		private void controlFlowReduceScope() {}

		@Pointcut("within(yamtl.core.YAMTLModule) && execution(* applyOutputElementAction(..))")
		private void controlFlowOutputActionScope() {}

		
		/* 
		 * MATCHING 
		 */
//		@After("cflowbelow(controlFlowMatchingScope()) && !cflowbelow(controlFlowLocalFilterScope()) && !cflowbelow(controlFlowEvaluateCallableScope()) && !cflowbelow(controlFlowInsertTrafostepScope()) && syntacticScope() && target(org.eclipse.emf.ecore.EObject) && execution(* *..get* (..))") // get(* *)
		@After("cflowbelow(controlFlowMatchingScope()) && !cflowbelow(controlFlowEvaluateCallableScope()) && !cflowbelow(controlFlowInsertTrafostepScope()) && syntacticScope() && target(org.eclipse.emf.ecore.EObject) && execution(* *..get* (..))") // get(* *)
		public void featureGetCallInMatchingCommon(JoinPoint thisJoinPoint) {
			module.featureGetCall_matching_common(thisJoinPoint);
		}

//		@After("(cflowbelow(controlFlowLocalFilterScope()) || cflowbelow(controlFlowEvaluateCallableScope())) && syntacticScope() && target(org.eclipse.emf.ecore.EObject) && execution(* *..get* (..))") // get(* *)
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
//			System.out.println("-- featureGetCallInContextualElement");
		}
		
		
		/**
		 * To track dependencies in output objects only.
		 * 
		 * Uncomment as required by the use case.
		 * 
		 */
//		@After("cflowbelow(controlFlowReduceScope()) && syntacticScope() && target(org.eclipse.emf.ecore.EObject) && execution(* *..set* (..))")
//		public void featureSetCallInReduce(JoinPoint thisJoinPoint) {
//			if (
//					(module.executionMode == ExecutionMode.INCREMENTAL)
//					|| 
//					(module.executionMode == ExecutionMode.PROPAGATION)
//			) {
//				if (module.incrementalGranularity == IncrementalGranularity.TRAFO_STEP) {
//					module.featureSetCallInReduce(thisJoinPoint);
//				}
//			}
//		}
	//	
//		@After("within(java.util.*) && (cflowbelow(controlFlowReduceScope()) ) && syntacticScope() && target(java.util.Collection) "
//				+ "&& ("
//					+ "call(* java.util.Collection.add(..)) || "
//					+ "call(* java.util.Collection.addAll(..)) ||"
//					+ "call(* java.util.Collection.remove(..)) ||"
//					+ "call(* java.util.Collection.removeAll(..)) ||"
//					+ "call(* java.util.Collection.clear(..)) ||"
//					+ "call(* java.util.Collection.retainAll(..))"
//				+ ")") 
//		public void collectionModification(JoinPoint thisJoinPoint) {
//			if (module != null) {
//				module.collectionModification(thisJoinPoint);
//			}
//		}
	
}