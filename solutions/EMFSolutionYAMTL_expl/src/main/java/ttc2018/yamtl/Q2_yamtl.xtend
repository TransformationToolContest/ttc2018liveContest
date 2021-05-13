package ttc2018.yamtl

import SocialNetwork.Comment
import SocialNetwork.SocialNetworkPackage
import SocialNetwork.Submission
import SocialNetwork.User
import java.util.List
import java.util.Map
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.Accessors
import yamtl.core.YAMTLModule
import yamtl.incremental.YAMTLFeatureValueChange

import static yamtl.dsl.Rule.*

import static extension ttc2018.yamtl.Util.addIfIsThreeBest
import static extension ttc2018.yamtl.Util.getBestThree

class Q2_yamtl extends YAMTLModule {
	val SN = SocialNetworkPackage.eINSTANCE  
	
	@Accessors
	val List<Pair<Submission,Integer>> threeBestCandidates = newArrayList

	@Accessors
	val List<Submission> candidatesWithNilScore = newArrayList

	@Accessors
	var Map<Comment,FriendComponentUtil_UF> componentMap
	
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
							var fc = componentMap.get(comment)
							if (fc===null) {
								fc = new FriendComponentUtil_UF(comment.likedBy)
								componentMap.put(comment, fc)
							} else {
								val map = this.fetch('dirtyFeatures') as Map<EObject,List<YAMTLFeatureValueChange>>
								for (e: map.entrySet) {
									for (fv: e.value) {
										switch(fv.featureName) {
											case 'likedBy': { fc.addLikedBy(fv.value as User) }
											case 'friends': { fc.addFriendship(e.key as User, fv.value as User)	}
										}
									}
								}
							}
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
