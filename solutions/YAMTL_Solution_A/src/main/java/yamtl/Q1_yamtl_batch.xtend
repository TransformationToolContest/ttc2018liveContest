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

class Q1_yamtl_batch extends YAMTLModule {
	@Accessors
	val public Map<Post,Integer> postToScore = newHashMap
	
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

					postToScore.put(post,score)

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

}