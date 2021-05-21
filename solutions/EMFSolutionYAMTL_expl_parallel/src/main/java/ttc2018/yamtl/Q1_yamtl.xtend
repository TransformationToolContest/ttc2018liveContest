package ttc2018.yamtl

import SocialNetwork.Comment
import SocialNetwork.Post
import SocialNetwork.SocialNetworkPackage
import SocialNetwork.Submission
import java.util.List
import java.util.Map
import java.util.concurrent.ConcurrentHashMap
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.Accessors
import yamtl.core.YAMTLModule
import yamtl.incremental.ChangeDescriptionAnalysisUtil.YAMTLChangeType

import static yamtl.dsl.Rule.*
import static extension ttc2018.yamtl.Util.*

/* score uses eAllContents */
class Q1_yamtl extends YAMTLModule {
	
	val SN = SocialNetworkPackage.eINSTANCE  

	@Accessors
	val static threeBestCandidates = new ConcurrentHashMap<YAMTLModule,List<Pair<Submission,Integer>>>()
	
	new () {
		threeBestCandidates.put(this, newArrayList)
		
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
									score += 10 + (comment as Comment).likedBy.size
								}									
							}
							val threeBestCandidatesLocalList = threeBestCandidates.get(this)
							threeBestCandidatesLocalList.addIfIsThreeBest(post, score)
							return true
						}
						false
					]
				.query
		])
	}
	
	// HELPERS
	def static getBestThree() {
		threeBestCandidates.values.flatten.sort([a,b| 
			val result = a.value.compareTo(b.value) * -1
			if (result !== 0) {
				return result
			} else {
				a.key.timestamp.compareTo(b.key.timestamp) * -1
			}
		]).take(3).toList.getBestThree(newArrayList)
	}
}