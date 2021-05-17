package ttc2018;

import Changes.ModelChange
import Changes.ModelChangeSet
import SocialNetwork.SocialNetworkPackage
import org.eclipse.emf.common.util.EList
import ttc2018.yamtl.Q2_yamtl
import yamtl.core.YAMTLModule.ExecutionMode
import yamtl.core.YAMTLModule.ExecutionPhase
import yamtl.incremental.ChangeDescriptionAnalysisUtil.YAMTLChangeType

class SolutionQ2 extends Solution {

	new() {
		xform = new Q2_yamtl()
		xform.selectedExecutionPhases = ExecutionPhase.MATCH_ONLY
		xform.fromRoots = false
		xform.executionMode = ExecutionMode.INCREMENTAL
		xform.initLocationsWhenLoading = true
	}

	override String Initial() {
		xform.changeSpecification = #{
			SocialNetworkPackage.eINSTANCE.user -> #{ 
				'friends' -> YAMTLChangeType.ADD 
			},
			SocialNetworkPackage.eINSTANCE.post -> #{
				'comments' ->  YAMTLChangeType.ADD
			},
			SocialNetworkPackage.eINSTANCE.comment -> #{
				'comments' ->  YAMTLChangeType.ADD,
				'likedBy' -> YAMTLChangeType.ADD 
			}
		}		
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
