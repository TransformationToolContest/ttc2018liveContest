package ttc2018;

import yamtl.core.YAMTLModule.ExecutionMode
import yamtl.core.YAMTLModule.ExecutionPhase
import yamtl.core.YAMTLModule.ExtentTypeModifier
import yamtl.Q2_yamtl

class SolutionQ2 extends Solution {

	new() {
		xform = new Q2_yamtl
		xform.stageUpperBound = 1
		xform.extentTypeModifier = ExtentTypeModifier.LIST
		xform.selectedExecutionPhases = ExecutionPhase.MATCH_ONLY
		xform.fromRoots = false
		xform.executionMode = ExecutionMode.INCREMENTAL
	}

	override String Initial() {
		xform.execute()
		(xform as Q2_yamtl).bestThree.map[id].join('|')
	}

	override String Update(String deltaName) {
		xform.propagateDelta('sn', deltaName)
		(xform as Q2_yamtl).bestThree.map[id].join('|')
	}

}
