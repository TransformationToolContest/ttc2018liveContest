package ttc2018;

import ttc2018.yamtl.Q2_yamtl
import yamtl.core.YAMTLModule.ExecutionMode
import yamtl.core.YAMTLModule.ExecutionPhase

class SolutionQ2 extends Solution {

	new() {
		xform = new Q2_yamtl
		xform.selectedExecutionPhases = ExecutionPhase.MATCH_ONLY
		xform.fromRoots = false
		xform.executionMode = ExecutionMode.INCREMENTAL
		xform.initLocationsWhenLoading = true
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
