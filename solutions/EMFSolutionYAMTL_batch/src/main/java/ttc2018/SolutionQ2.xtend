package ttc2018;

import ttc2018.yamtl.Q2_yamtl
import yamtl.core.YAMTLModule.ExecutionPhase

class SolutionQ2 extends Solution {

	new() {
		xform = new Q2_yamtl
		xform.selectedExecutionPhases = ExecutionPhase.MATCH_ONLY
		xform.fromRoots = false
		xform.initLocationsWhenLoading = true
	}

	override String Initial() {
		xform.enableUpfrontResizingWithOverflow()
		xform.execute()
		(xform as Q2_yamtl).bestThree.map[id].join('|')
	}

	override String Update(String deltaName) {
		(xform as Q2_yamtl).threeBestCandidates.clear()
		(xform as Q2_yamtl).candidatesWithNilScore.clear()
		xform.reset()
		
		xform.applyDelta('sn', deltaName)

		xform.execute()
		(xform as Q2_yamtl).getBestThree().map[id].join('|')
	}

}
