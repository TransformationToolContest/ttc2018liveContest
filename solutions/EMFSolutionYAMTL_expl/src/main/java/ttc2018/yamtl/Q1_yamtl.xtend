package ttc2018.yamtl

import SocialNetwork.Comment
import SocialNetwork.Post
import SocialNetwork.SocialNetworkPackage
import SocialNetwork.Submission
import java.util.List
import java.util.Map
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.Accessors
import yamtl.core.YAMTLModule
import yamtl.incremental.ChangeDescriptionAnalysisUtil.YAMTLChangeType

import static yamtl.dsl.Rule.*

import static extension ttc2018.yamtl.Util.addIfIsThreeBest
import static extension ttc2018.yamtl.Util.getBestThree

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
						var score = 0
						if (post.comments?.size>0) {
							val map = this.fetch('dirtyObjects') as Map<EObject,List<YAMTLChangeType>>
							if (map === null || map.isEmpty) {
								val allComments = post.eAllContents
								while (allComments.hasNext) {
									score += 10 + (allComments.next as Comment).likedBy.size
								}
							}
							else {
								for (comment: map.keySet) {
									switch (comment) {
										Comment: { score += 10 + comment.likedBy.size }
									}
								}									
							}
							threeBestCandidates.addIfIsThreeBest(post, score)
							return true
						}
						false
					]
				.query
		])
	}
	
	// HELPERS
	def getBestThree() {
		threeBestCandidates.getBestThree(newArrayList)
	}	
}