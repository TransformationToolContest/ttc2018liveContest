package ttc2018;

import yamtl.Q1_yamtl
import yamtl.core.YAMTLModule.ExecutionPhase
import yamtl.core.YAMTLModule.ExtentTypeModifier

class SolutionQ1 extends Solution {
	
	new() {
		xform = new Q1_yamtl
		xform.stageUpperBound = 1
		xform.extentTypeModifier = ExtentTypeModifier.LIST
		xform.selectedExecutionPhases = ExecutionPhase.MATCH_ONLY
		xform.fromRoots = false
//		xform.executionMode = ExecutionMode.INCREMENTAL
		
	}
	
	override String Initial() {
		xform.execute()
		(xform as Q1_yamtl).bestThree.map[id].join('|')
	}

	override String Update(String deltaName) {
		(xform as Q1_yamtl).threeBestCandidates.clear()
		(xform as Q1_yamtl).candidatesWithNilScore.clear()
		xform.reset()
		
		xform.applyDelta('sn', deltaName)
		
		xform.execute()
		(xform as Q1_yamtl).bestThree.map[id].join('|')
	}
}

