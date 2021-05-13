package ttc2018;

import Changes.ModelChange
import Changes.ModelChangeSet
import org.eclipse.emf.common.util.EList
import ttc2018.yamtl.Q1_yamtl
import yamtl.core.YAMTLModule.ExecutionMode
import yamtl.core.YAMTLModule.ExecutionPhase

class SolutionQ1 extends Solution {
	
	new() {
		xform = new Q1_yamtl()
		xform.selectedExecutionPhases = ExecutionPhase.MATCH_ONLY
		xform.fromRoots = false
		xform.executionMode = ExecutionMode.INCREMENTAL
		xform.initLocationsWhenLoading = true		
	}
	
	override String Initial() {
		xform.execute()
		(xform as Q1_yamtl).bestThree.map[id].join('|')
	}

	override String Update(String deltaName, ModelChangeSet changes) {
		val nmfChange = [|
	        val EList<ModelChange> coll = changes.getChanges();
			for (ModelChange change : coll) {
				change.apply();
			}
			void
		]
		xform.propagateDelta('sn', deltaName, nmfChange)
		(xform as Q1_yamtl).bestThree.map[id].join('|')
	}
}

