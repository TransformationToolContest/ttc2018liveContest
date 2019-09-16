package ttc2018

import Changes.ModelChange
import Changes.ModelChangeSet
import SocialNetwork.Post
import java.util.HashMap
import org.eclipse.emf.common.util.EList
import org.eclipse.papyrus.aof.core.IBox
import org.eclipse.papyrus.aof.core.IOne

import static extension ttc2018.AllContents.*

class SolutionQ1 extends Solution implements AOFExtensions {
	
	public final static int COMMENT_SCORE = 10
	
	public final static int LIKE_SCORE = 1

	val extension SN = new SN

	var IBox<String> result

	var IBox<Post> posts

	override String Initial() {
		posts = socialNetwork._posts

		result = queryQ1
		return result.join("|")
	}

	override String Update(ModelChangeSet changes) {
		var EList<ModelChange> coll = changes.getChanges()
		for (ModelChange change : coll) {
			change.apply()
		}
		return result.join("|")
	}

	def private queryQ1() {
		val answer = posts
		.sortedBy([computeScore], [
			_timestamp
			.asOne(null)
		])
		.take(3).collect[
			id
//			'''«id» («computeScore.get»)'''
		]
//		.inspect("result: ")

		return answer
	}

	// having a cache is mandatory, apparently because of how SortedBy resets oldValue
	val scoreByPost = new HashMap<Post, IOne<Integer>>
	def private computeScore(Post p) {
		return scoreByPost.get(p) ?: {
			val comments = p._allContents(SN.Comment)
			val score = comments.size * COMMENT_SCORE
						+
						comments
						// doing .collectMutable[it?.likedBy?.size ?: emptyOne).sum
						// may be more efficient because fewer inner observers
							.likedBy.size.sum * LIKE_SCORE
	
			val r = score.asOne(0)
			scoreByPost.put(p, r)
			r
		}
	}

	def *(IBox<Integer> it, int b) {
		collect[it * b]
	}

	def +(IBox<Integer> it, IBox<Integer> b) {
		zipWith(b)[$0 + $1]
	}

	def -(IBox<Integer> it) {
		collect[-it]
	}
}
