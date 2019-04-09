package yamtl

import SocialNetwork.Comment
import SocialNetwork.SocialNetworkPackage
import SocialNetwork.Submission
import java.util.List
import org.eclipse.xtend.lib.annotations.Accessors
import yamtl.core.YAMTLModule
import yamtl.dsl.Rule

import static extension yamtl.Util.addIfIsThreeBest
import static extension yamtl.Util.sum
import static extension yamtl.Util.getBestThree

class Q2_yamtl extends YAMTLModule {
	val SN = SocialNetworkPackage.eINSTANCE  
	
	@Accessors
	val List<Pair<Submission,Integer>> threeBestCandidates = newArrayList

	@Accessors
	val List<Submission> candidatesWithNilScore = newArrayList
	
	new () {
		header().in('sn', SN).out('out', SN)
		ruleStore( newArrayList(
			new Rule('UserComponentsByComment')
				.in('comment', SN.comment)
					.filter[
						val comment = 'comment'.fetch as Comment
						var score = 0
						var matches = false
						
						if (comment.likedBy.size > 0) {
							val fc = new FriendComponentUtil(comment.likedBy)
							score = fc.components.map[size * size].sum
							threeBestCandidates.addIfIsThreeBest(comment, score)
							matches = true
						} else {
							candidatesWithNilScore.add(comment)
						}
						matches
					]
					.build()
				.out('commentAux', SN.comment, []).build()
				.build()
		))
	}
	
	// HELPER
	def getBestThree() {
		threeBestCandidates.getBestThree(candidatesWithNilScore)
	}
}