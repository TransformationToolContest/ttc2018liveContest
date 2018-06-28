package ttc2018;

import yamtl.core.YAMTLModule.ExecutionMode
import yamtl.core.YAMTLModule.ExtentTypeModifier
import yamtl.Q1_yamtl_batch_v2_threeBest

// SolutionQ1_v2_threeBest
public class SolutionQ1 extends Solution {
	
	new() {
		xform = new Q1_yamtl_batch_v2_threeBest
		xform.stageUpperBound = 1
		xform.extentTypeModifier = ExtentTypeModifier.LIST
		xform.fromRoots = false
		xform.executionMode = ExecutionMode.INCREMENTAL
		
	}
	
	override String Initial() {
		xform.execute()
		val list = Q1_yamtl_batch_v2_threeBest.trimToBestThree((xform as Q1_yamtl_batch_v2_threeBest).controversialPosts)
		list.map[it.key.id].join('|')
	}

	override String Update(String deltaName) {
		xform.propagateDelta('sn', deltaName)
		val list = Q1_yamtl_batch_v2_threeBest.trimToBestThree((xform as Q1_yamtl_batch_v2_threeBest).controversialPosts)
		list.map[it.key.id].join('|')
	}


//	def private selectThree(Map<Post,Integer> postToScore) {
//		postToScore.entrySet
//			.sortWith([p1,p2|
//				val result = Integer::compare(p1.value, p2.value) * -1 
//				if ( result == 0) 
//					p1.key.timestamp.compareTo(p2.key.timestamp) * -1
//				else
//					result
//			]).take(3)
//			.map[it.key.id].join('|') 
//	}
	

}
