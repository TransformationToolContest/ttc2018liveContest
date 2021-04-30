package ttc2018.yamtl

import SocialNetwork.Comment
import SocialNetwork.Post
import SocialNetwork.SocialNetworkPackage
import SocialNetwork.Submission
import java.util.List
import org.eclipse.xtend.lib.annotations.Accessors
import yamtl.core.YAMTLModule
import static yamtl.dsl.Rule.*

import static extension ttc2018.yamtl.Util.getBestThree
import static extension ttc2018.yamtl.Util.addIfIsThreeBest

/* score uses eAllContents */
class Q1_yamtl extends YAMTLModule {
	
	val SN = SocialNetworkPackage.eINSTANCE  

	@Accessors
	val List<Pair<Submission,Integer>> threeBestCandidates = newArrayList
	
	new () {
		header().in('sn', SN).out('out', SN)
		
		ruleStore( #[
			rule('CountPosts')
				.in('post', SN.post)
					.filter[
						val post = 'post'.fetch as Post
						var matches = false
						
						val score = post.score
						if (score > 0) {
							threeBestCandidates.addIfIsThreeBest(post, score)
							matches = true
						} 
						matches
					]
				.query
		])
	}
	
	// HELPERS
	def getBestThree() {
		threeBestCandidates.getBestThree(#[])
	}
		
	def static Integer getScore(Submission submission) {
		var score = 0
		val allComments = submission.eAllContents
		while (allComments.hasNext) {
			score += 10 + (allComments.next as Comment).likedBy.size
		}
		score
	}
	
}