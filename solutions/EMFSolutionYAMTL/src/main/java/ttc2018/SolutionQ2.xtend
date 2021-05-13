package ttc2018;

import Changes.ModelChange
import Changes.ModelChangeSet
import SocialNetwork.Comment
import java.util.HashMap
import org.eclipse.emf.common.util.EList
import ttc2018.yamtl.FriendComponentUtil_UF
import ttc2018.yamtl.Q2_yamtl
import yamtl.core.YAMTLModule.ExecutionMode
import yamtl.core.YAMTLModule.ExecutionPhase

class SolutionQ2 extends Solution {

	new() {
		xform = new Q2_yamtl()
		xform.selectedExecutionPhases = ExecutionPhase.MATCH_ONLY
		xform.fromRoots = false
		xform.executionMode = ExecutionMode.INCREMENTAL
		xform.initLocationsWhenLoading = true
	}

	override String Initial() {
		(xform as Q2_yamtl).componentList = new HashMap<Comment,FriendComponentUtil_UF>(xform.initialSizeFactor)
		xform.execute()
		(xform as Q2_yamtl).bestThree.map[id].join('|')
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
		(xform as Q2_yamtl).bestThree.map[id].join('|')
	}

}
