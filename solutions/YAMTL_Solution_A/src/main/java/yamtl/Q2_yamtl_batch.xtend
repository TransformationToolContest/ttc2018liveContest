package yamtl

import SocialNetwork.Comment
import SocialNetwork.SocialNetworkPackage
import java.util.List
import java.util.Map
import org.eclipse.xtend.lib.annotations.Accessors
import yamtl.core.YAMTLModule
import yamtl.dsl.Rule

class Q2_yamtl_batch extends YAMTLModule {
	@Accessors
	val Map<Comment,Integer> influentialComments = newHashMap
	
	
	val SN = SocialNetworkPackage.eINSTANCE  
	
	new () {
		header().in('sn', SN).out('out', SN)
		
		ruleStore( newArrayList(
			
			new Rule('UserComponentsByComment')
				.in('comment', SN.comment).build()
				.out('commentAux', SN.comment, [ 

					val comment = 'comment'.fetch as Comment
					
					var score = 0
					if (comment.likedBy.size > 0) {
						val fc = FriendsComponents.computeComponents(comment.likedBy)
						score = fc.squaredComponentSizes.sum
					}
					influentialComments.put(comment, score)
					influentialComments.trimToBestThree

				]).build()
				.build()
	
		))
		
	}
	

	
	/**
	 * HELPERS
	 */
	def public static trimToBestThree(Map<Comment,Integer> map) {
		val list = map.entrySet.sortWith([c1,c2|
			val result = - Integer::compare(c1.value, c2.value)  
			if ( result == 0) {
//				println('''comparing 
//				«c1.key.id» -> «c1.key.timestamp.toString» with 
//				«c2.key.id» -> «c2.key.timestamp.toString»''')
				
				- c1.key.timestamp.compareTo(c2.key.timestamp)
			} else
				result
		])
		if(list.size>3) map.remove(list.last.key)
		list
	}
	
	def private sum(Iterable<Integer> list) {
		list.reduce[v1, v2 | v1+v2]
	}

}