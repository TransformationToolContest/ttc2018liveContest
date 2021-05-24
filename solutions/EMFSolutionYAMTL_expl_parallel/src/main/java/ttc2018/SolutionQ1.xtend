package ttc2018;

import Changes.ModelChange
import Changes.ModelChangeSet
import SocialNetwork.SocialNetworkPackage
import org.eclipse.emf.common.util.EList
import ttc2018.yamtl.Q1_yamtl
import yamtl.core.YAMTLModule.ExecutionMode
import yamtl.core.YAMTLModule.ExecutionPhase
import yamtl.incremental.ChangeDescriptionAnalysisUtil.YAMTLChangeType

class SolutionQ1 extends Solution {
	
	new() {
		xform = new Q1_yamtl()
		xform.selectedExecutionPhases = ExecutionPhase.MATCH_ONLY
		xform.fromRoots = false
		xform.executionMode = ExecutionMode.INCREMENTAL
		xform.initLocationsWhenLoading = true
		xform.enableExplicitIncrementality = true
		xform.numberOfProcessors = 2
	}
	
	override String Initial() {
		xform.changeSpecification = #{
			SocialNetworkPackage.eINSTANCE.post -> #{ 
				'comments' -> YAMTLChangeType.ADD 
			},
			SocialNetworkPackage.eINSTANCE.comment -> #{ 
				'comments' -> YAMTLChangeType.ADD, 
				'likedBy' -> YAMTLChangeType.ADD 
			}
		}		
		xform.execute()
		Q1_yamtl.bestThree.map[it.id].join('|')
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
		Q1_yamtl.bestThree.map[it.id].join('|')
	}
}

