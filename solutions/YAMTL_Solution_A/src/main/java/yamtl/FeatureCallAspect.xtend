package yamtl

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.eclipse.emf.ecore.EObject
import yamtl.core.YAMTLModule

@Aspect
class FeatureCallAspect {
	var YAMTLModule module

	///////////////////////////////////////////////////////////////////////
	// PROJECT SPECIFIC
	///////////////////////////////////////////////////////////////////////
	@Pointcut("within(SocialNetwork.impl.*)")
	def private void syntacticScope() {}
	
	///////////////////////////////////////////////////////////////////////
	// GENERIC
	///////////////////////////////////////////////////////////////////////
	@Before("within(yamtl.core.YAMTLModule) && execution(void execute())")
	def public void getModule(JoinPoint thisJoinPoint) {
		module = thisJoinPoint.this as YAMTLModule
	}

	@Pointcut("(
		(within(yamtl.utils.ReduceUtil) && execution(void reduce(yamtl.core.MatchMap)))
	)")
	def private void controlFlowReduceScope() {}

	@Pointcut("(
		(within(yamtl.utils.MatcherUtil) && execution(* findMatchesAndSchedule(..)))
	)")
	def private void controlFlowMatchingScope() {}

	@Pointcut("(
		(within(yamtl.utils.MatcherUtil) && execution(* insertTupleMatch(..)))
	)")
	def private void controlFlowInsertTrafostepScope() {}


	@After("
		cflowbelow(controlFlowMatchingScope())
		&& !cflowbelow(controlFlowInsertTrafostepScope())
		&& syntacticScope()
		&& target(org.eclipse.emf.ecore.EObject) 
		&& get(* *)")
	def public void featureGetCallInMatching(JoinPoint thisJoinPoint) {
		val object = thisJoinPoint.target as EObject
		val featureName = thisJoinPoint.signature.name
		
		if ((module!==null) && (object !== null)) {
			
			module.engine.featureCallList.add(object -> featureName)
			
		}
	}
	
	@After("
		cflowbelow(controlFlowReduceScope())
		&& syntacticScope()
		&& target(org.eclipse.emf.ecore.EObject) 
		&& get(* *)")
	def public void featureGetCallInReduce(JoinPoint thisJoinPoint) {
		val object = thisJoinPoint.target as EObject
		val featureName = thisJoinPoint.signature.name
		
		if ((module!==null) && (object !== null)) {
			
			val trafoStep = module.engine.currentTrafoStepStack.peek
			
			var featureMap = module.engine.featureCallToTrafoStep.get(object)
			if (featureMap === null) {
				featureMap = newLinkedHashMap()
				featureMap.put(featureName, newArrayList(trafoStep))
				module.engine.featureCallToTrafoStep.put(object, featureMap)
			} else {
				var trafoStepList = featureMap.get(featureName)
				if (trafoStepList === null) {
					featureMap.put(featureName, newArrayList(trafoStep))
				} else {
					trafoStepList.add(trafoStep)
				}
			}
		}
	}

}