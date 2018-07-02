package ttc2018;

import yamtl.Q2_yamtl_batch
import yamtl.core.YAMTLModule.ExecutionMode
import yamtl.core.YAMTLModule.ExtentTypeModifier

public class SolutionQ2 extends Solution {
	new() {
		xform = new Q2_yamtl_batch
		xform.stageUpperBound = 1
		xform.extentTypeModifier = ExtentTypeModifier.LIST
		xform.fromRoots = false
		xform.executionMode = ExecutionMode.INCREMENTAL
	}

	override String Initial() {
		xform.execute()
		val list = Q2_yamtl_batch.trimToBestThree(
			(xform as Q2_yamtl_batch).influentialComments
		)
		list.map[it.key.id].join('|')
	}

	override String Update(String deltaName) {
		xform.propagateDelta('sn', deltaName)
		val list = Q2_yamtl_batch.trimToBestThree(
			(xform as Q2_yamtl_batch).influentialComments
		)
		list.map[it.key.id].join('|')
	}
}

