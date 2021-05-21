package ttc2018.yamtl

import SocialNetwork.Comment
import SocialNetwork.SocialNetworkPackage
import SocialNetwork.Submission
import SocialNetwork.User
import java.util.List
import java.util.Map
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.Accessors
import yamtl.core.YAMTLModule
import yamtl.incremental.YAMTLFeatureValueChange

import static yamtl.dsl.Rule.*
import static extension ttc2018.yamtl.Util.*

class Q2_yamtl extends YAMTLModule {
	val SN = SocialNetworkPackage.eINSTANCE  
	
	@Accessors
	val static threeBestCandidates = new ConcurrentHashMap<YAMTLModule,List<Pair<Submission,Integer>>>()

	@Accessors
	val static candidatesWithNilScore = new ConcurrentHashMap<YAMTLModule,List<Submission>>()

	@Accessors
	var static ConcurrentMap<Comment,FriendComponentUtil_UF> componentMap = new ConcurrentHashMap<Comment,FriendComponentUtil_UF>()
	
	new () {
		threeBestCandidates.put(this, newArrayList)
		candidatesWithNilScore.put(this, newArrayList)
		
		header().in('sn', SN).out('out', SN)
		ruleStore( newArrayList(
			rule('UserComponentsByComment')
				.in('comment', SN.comment)
					.filter[
						val comment = 'comment'.fetch as Comment
						var score = 0
						var matches = false
						
						val threeBestCandidatesLocalList = threeBestCandidates.get(this)
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
							threeBestCandidatesLocalList.addIfIsThreeBest(comment, score)
							matches = true
						} else {
							if (threeBestCandidatesLocalList.size <= 3) {
								val candidatesWithNilScoreLocalList = candidatesWithNilScore.get(this)
								candidatesWithNilScoreLocalList.add(comment)
							}
						}
						matches
					]
				.query
		))
	}
	
	// HELPER
	def static getBestThree() {
		val bestThree = threeBestCandidates.values.flatten.sort([a,b| 
			val result = a.value.compareTo(b.value) * -1
			if (result !== 0) {
				return result
			} else {
				a.key.timestamp.compareTo(b.key.timestamp) * -1
			}
		]).take(3)
		if (bestThree.size<3) {
			return bestThree.toList.getBestThree(candidatesWithNilScore.values.flatten.toList)
		}
		return bestThree.map[it.key]
	}
	
}
