package ttc2018

import Changes.ModelChange
import Changes.ModelChangeSet
import SocialNetwork.Comment
import SocialNetwork.User
import de.fzi.se.Layering
import java.util.HashMap
import org.eclipse.emf.common.util.EList
import org.eclipse.papyrus.aof.core.IBox
import org.eclipse.papyrus.aof.core.IOne

import static extension ttc2018.AllContents.*

class SolutionQ2 extends Solution implements AOFExtensions {
	
	val extension SN = new SN
	
	var IBox<String> result
	
	override String Initial() {
		SN.Submission.defaultInstance = SN.Comment.defaultInstance
		result = queryQ2
		return result.join("|")
	}

	override String Update(ModelChangeSet changes) {
		var EList<ModelChange> coll = changes.getChanges()
		for (ModelChange change : coll) {
			change.apply()
		}
		return result.join("|")
	}

	def <E> emptyOne() {
		IBox.ONE as IOne<E>
	}

	def private queryQ2() {
		val comments =
			socialNetwork
				._allContents(SN.Comment)
		
		val answer = comments.sortedBy([
			computeScore
		], [
			_timestamp.asOne(null)
		]).take(3)
//		.collect['''«id» («computeScore»)''']
		.collect[id]
		
		return answer
	}
	
	val scoreByComment = new HashMap<Comment, IOne<Integer>>
	def computeScore(Comment c) {
		return scoreByComment.get(c) ?: {
//*			// more memory-hungry fine-grained incremental version
			val s = c._likedBy
					 .connectedComponents[u |
//						u._friends.selectMutable[f |
//							f._likes.select[it == c].notEmpty
//						]
						u._friends.intersection(c._likedBy)
					].collectMutable[it?.size?.square ?: emptyOne].sum
/*/			// more memory-frugal coarse-grained incremental version
			// DONE: create an active operation that listens to a bunch of boxes, and recomputes completely if one these boxes changes, whatever the change
			// this would enable coarse-grained incrementality where necessary
			val s = new OpaqueOperation[
				val layering = new Layering[User u | u.friends.filter[likes.contains(c)]]
				val comps = layering.CreateLayers(c.likedBy)
				comps.map[size*size].reduce[$0+$1] ?: 0
			]
			.observe(c._likedBy)	// no need to observe opposite (i.e., User.likes)
			.<User>observeInners(c._likedBy.collect[_friends])
			.result
/**/

			scoreByComment.put(c, s)
//			println('''«c.id» : «s.get»''')
			s
		}
	}

	val squareCache = new HashMap<IBox<Integer>, IBox<Integer>>
	def square(IBox<Integer> source) {
		squareCache.computeIfAbsent(source)[collect[it * it]]
	}

	def -(IBox<Integer> it) {
		collect[-it]
	}
}
