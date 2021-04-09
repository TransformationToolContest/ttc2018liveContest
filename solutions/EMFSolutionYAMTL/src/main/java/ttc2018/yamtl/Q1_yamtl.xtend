package ttc2018.yamtl

import SocialNetwork.Comment
import SocialNetwork.Post
import SocialNetwork.SocialNetworkPackage
import SocialNetwork.Submission
import java.util.List
import org.eclipse.xtend.lib.annotations.Accessors
import yamtl.core.YAMTLModule
import yamtl.dsl.Rule

import static extension ttc2018.yamtl.Util.getBestThree
import static extension ttc2018.yamtl.Util.addIfIsThreeBest
import static extension ttc2018.yamtl.Util.sum

class Q1_yamtl extends YAMTLModule {
	
	val SN = SocialNetworkPackage.eINSTANCE  

	@Accessors
	val List<Pair<Submission,Integer>> threeBestCandidates = newArrayList
	
	@Accessors
	val List<Submission> candidatesWithNilScore = newArrayList
	
	new () {
		header().in('sn', SN).out('out', SN)
		
//		ruleStore( newArrayList(
//			new Rule('CountPosts')
//				.in('post', SN.post)
//					.filter[
//						val post = 'post'.fetch as Post
//						var int score = 0
//						var matches = false
//						
//						val commentList = post.allComments
//						if (commentList?.size > 0) {
//							score = commentList.map[c | 10 + (c as Comment).likedBy.size].sum
//							threeBestCandidates.addIfIsThreeBest(post, score)
//							matches = true
//						} else {
//							candidatesWithNilScore.add(post)
//						}
//						matches
//					]
//					.build()
//				.out('postAux', SN.post, []).build()
//				.build()
//		))
		ruleStore( newArrayList(
			new Rule('CountPosts')
				.in('post', SN.post)
					.filter[
						val post = 'post'.fetch as Post
						var matches = false
						
						val score = post.score
						if (score > 0) {
							threeBestCandidates.addIfIsThreeBest(post, score)
							matches = true
						} else {
							candidatesWithNilScore.add(post)
						}
						matches
					]
					.build()
				.out('postAux', SN.post, []).build()
				.build()
		))
	}
	
	// HELPERS
	def getBestThree() {
		threeBestCandidates.getBestThree(candidatesWithNilScore)
	}
		
//	def static List<Comment> getAllComments(Submission submission) {
//		if (submission.comments?.size>0) {
//			val commentList = newArrayList
//			commentList.addAll(submission.comments)
//			for (comment: submission.comments) {
//				val list = comment.getAllComments
//				if (list!==null) commentList.addAll(list)
//			}
//			commentList
//		}
//	}
	
	
	
	
	def static Integer getScore(Submission submission) {
		var score = 0
		if (submission.comments?.size>0) {
			score = submission.comments.map[c | 10 + (c as Comment).likedBy.size].sum
			for (comment: submission.comments) {
				score += comment.score
			}
		}
		score
	}
	
}