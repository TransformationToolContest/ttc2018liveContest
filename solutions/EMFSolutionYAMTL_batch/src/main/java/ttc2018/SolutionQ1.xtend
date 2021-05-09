package ttc2018;

import ttc2018.yamtl.Q1_yamtl
import yamtl.core.YAMTLModule.ExecutionPhase

class SolutionQ1 extends Solution {
	
	new() {
		xform = new Q1_yamtl
		xform.selectedExecutionPhases = ExecutionPhase.MATCH_ONLY
		xform.fromRoots = false
		xform.initLocationsWhenLoading = true
	}
	
	override String Initial() {
		xform.enableUpfrontResizingWithOverflow()
		xform.execute()
		(xform as Q1_yamtl).bestThree.map[id].join('|')
	}

	override String Update(String deltaName) {
		(xform as Q1_yamtl).threeBestCandidates.clear()
		xform.reset()
		
		xform.applyDelta('sn', deltaName)
		
		xform.execute()
		(xform as Q1_yamtl).bestThree.map[id].join('|')
	}

}

