package ttc2018;

import SocialNetwork.Post
import java.util.Map
import yamtl.Q1_yamtl_batch
import yamtl.core.YAMTLModule.ExecutionMode
import yamtl.core.YAMTLModule.ExtentTypeModifier

// SolutionQ1_v1_noReverse
public class SolutionQ1 extends Solution {
	
	new() {
		
//		var String inputModelPath = '''/Users/ab373/Documents/ArturData/WORK/git/ttc2018liveContest/models/1/initial.xmi'''
//		
//		xform.stageUpperBound = 1
//		xform.extentTypeModifier = ExtentTypeModifier.LIST
//		xform.fromRoots = false
//
//		// PREPARE MODELS
//		xform.loadInputModels(#{'sn' -> inputModelPath})

		xform = new Q1_yamtl_batch
		xform.stageUpperBound = 1
		xform.extentTypeModifier = ExtentTypeModifier.LIST
		xform.fromRoots = false
		xform.executionMode = ExecutionMode.INCREMENTAL
		
	}
	
	override String Initial() {
		xform.execute()
		(xform as Q1_yamtl_batch).postToScore.selectThree
	}

	override String Update(String deltaName) {
		xform.propagateDelta('sn', deltaName)
		(xform as Q1_yamtl_batch).postToScore.selectThree
	}


	def private selectThree(Map<Post,Integer> postToScore) {
		postToScore.entrySet
			.sortWith([p1,p2|
				val result = Integer::compare(p1.value, p2.value) * -1 
				if ( result == 0) 
					p1.key.timestamp.compareTo(p2.key.timestamp) * -1
				else
					result
			]).take(3)
			.map[it.key.id].join('|') 
	}
	

}
