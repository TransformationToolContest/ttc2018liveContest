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
		val list = Q1_yamtl_batch_v2_threeBest
			.trimToBestThree((xform as Q1_yamtl_batch_v2_threeBest).controversialPosts)
		list.map[it.key.id].join('|')
	}

	override String Update(String deltaName) {
		xform.propagateDelta('sn', deltaName)
		val list = Q1_yamtl_batch_v2_threeBest
			.trimToBestThree((xform as Q1_yamtl_batch_v2_threeBest).controversialPosts)
		list.map[it.key.id].join('|')
	}
}

