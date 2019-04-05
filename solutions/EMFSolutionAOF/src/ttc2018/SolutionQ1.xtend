package ttc2018

import Changes.ModelChange
import Changes.ModelChangeSet
import SocialNetwork.Comment
import SocialNetwork.Post
import java.util.HashMap
import org.eclipse.emf.common.util.EList
import org.eclipse.papyrus.aof.core.IBox
import org.eclipse.papyrus.aof.core.IOne
import org.eclipse.papyrus.aof.core.IUnaryFunction

import static extension ttc2018.AllContents.*

class SolutionQ1 extends Solution {
	
	public final static int COMMENT_SCORE = 10
	
	public final static int LIKE_SCORE = 1

	val extension SN = new SN

	var IBox<String> result

	override String Initial() {
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
		val socialNetwork = this.socialNetwork
		val posts = socialNetwork._posts

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

	def static <E> take(IBox<E> it, int n) {
		new Take(it, n).result
	}

	def static <E> sortedBy(IBox<E> it, IUnaryFunction<E, IOne<? extends Comparable<?>>>...bodies) {
		new SortedBy(it, bodies).result
	}

	val scoreByPost = new HashMap<Post, IOne<Integer>>
	def private computeScore(Post p) {
		return scoreByPost.get(p) ?: {
			val comments = p._allContents(Comment)
			val score = comments.size * COMMENT_SCORE
						+
						comments
							.likedBy.size.sum * LIKE_SCORE
	
			val r = score.asOne(0)
			scoreByPost.put(p, r)
			r
		}
	}

	def static sum(IBox<Integer> it) {
		new Sum(it).result as IOne<Integer>
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
