package ttc2018;

import ttc2018.yamtl.Q1_yamtl
import yamtl.core.YAMTLModule.ExecutionMode
import yamtl.core.YAMTLModule.ExecutionPhase
import yamtl.core.YAMTLModule.ExtentTypeModifier

class SolutionQ1 extends Solution {
	
	new() {
		xform = new Q1_yamtl
		xform.selectedExecutionPhases = ExecutionPhase.MATCH_ONLY
		xform.fromRoots = false
		xform.executionMode = ExecutionMode.INCREMENTAL
		xform.initLocationsWhenLoading = true		
	}
	
	override String Initial() {
		xform.enableUpfrontResizingWithOverflow()
		xform.execute()
		(xform as Q1_yamtl).bestThree.map[id].join('|')
	}

	override String Update(String deltaName) {
		xform.propagateDelta('sn', deltaName)
		(xform as Q1_yamtl).bestThree.map[id].join('|')
	}
}

