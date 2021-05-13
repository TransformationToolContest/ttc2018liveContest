package ttc2018.yamtl

import SocialNetwork.Comment
import SocialNetwork.SocialNetworkPackage
import SocialNetwork.Submission
import java.util.List
import java.util.Map
import org.eclipse.xtend.lib.annotations.Accessors
import yamtl.core.YAMTLModule

import static yamtl.dsl.Rule.*

import static extension ttc2018.yamtl.Util.addIfIsThreeBest
import static extension ttc2018.yamtl.Util.getBestThree

class Q2_yamtl extends YAMTLModule {
	val SN = SocialNetworkPackage.eINSTANCE  
	
	@Accessors
	val List<Pair<Submission,Integer>> threeBestCandidates = newArrayList

	@Accessors
	val List<Submission> candidatesWithNilScore = newArrayList

	new () {
		header().in('sn', SN).out('out', SN)
		ruleStore( newArrayList(
			rule('UserComponentsByComment')
				.in('comment', SN.comment)
					.filter[
						val comment = 'comment'.fetch as Comment
						var score = 0
						var matches = false
						
						if (comment.likedBy.size > 0) {
							val fc = new FriendComponentUtil_UF(comment.likedBy)
							score = fc.score
							threeBestCandidates.addIfIsThreeBest(comment, score)
							matches = true
						} else {
							if (threeBestCandidates.size <= 3)
								candidatesWithNilScore.add(comment)
						}
						matches
					]
				.query
		))
	}
	
	// HELPER
	def getBestThree() {
		threeBestCandidates.getBestThree(candidatesWithNilScore)
	}
}


