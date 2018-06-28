package yamtl

import SocialNetwork.Comment
import SocialNetwork.Post
import SocialNetwork.SocialNetworkPackage
import java.util.List
import java.util.Map
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.EcoreUtil2
import yamtl.core.YAMTLModule
import yamtl.dsl.Rule

// Q1_yamtl_batch_v2_threeBest
class Q1_yamtl_batch_v2_threeBest extends YAMTLModule {
	@Accessors
	val Map<Post,Integer> controversialPosts = newHashMap
	
	val SN = SocialNetworkPackage.eINSTANCE  
	
	new () {
		header().in('sn', SN).out('out', SN)
		
		ruleStore( newArrayList(
			
			new Rule('CountPosts')
				.in('post', SN.post).build()
				.out('postAux', SN.post, [ 

					val post = 'post'.fetch as Post
					
					val commentList = EcoreUtil2.getAllContentsOfType(post, Comment)
					var int score = 0
					if (commentList.size > 0)
						score = commentList.map[c | 10 + c.likedBy.size].sum
					controversialPosts.put(post, score)
					controversialPosts.trimToBestThree
					
				]).build()
				.build()
	
		))
		
	}

	
	/**
	 * HELPERS
	 */
	def private sum(List<Integer> list) {
		list.reduce[v1, v2 | v1+v2]
	}
	
	def public static trimToBestThree(Map<Post,Integer> map) {
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
	
}