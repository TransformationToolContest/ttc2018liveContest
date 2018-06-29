package ttc2018

import Changes.ModelChange
import Changes.ModelChangeSet
import SocialNetwork.Comment
import java.util.HashMap
import org.eclipse.emf.common.util.EList
import org.eclipse.papyrus.aof.core.IBox
import org.eclipse.papyrus.aof.core.IOne
import org.eclipse.papyrus.aof.core.IUnaryFunction

import static extension ttc2018.SolutionQ1.*
import static extension ttc2018.AllContents.*


class SolutionQ2 extends Solution {
	
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
				._allContents(Comment)
		
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
			val s = c._likedBy
					 .layering[u |
						u._friends.selectMutable[f |
							f._likes.select[it == c].notEmpty
						]
					].collectMutable[it?.size?.square ?: emptyOne].sum
			scoreByComment.put(c, s)
//			println('''«c.id» : «s.get»''')
			s
		}
	}

	def <E> layering(IBox<E> it, IUnaryFunction<E, IBox<E>> accessor) {
		new Layering(it, accessor).result
	}

	def square(IBox<Integer> it) {
		collect[it * it]
	}

	def -(IBox<Integer> it) {
		collect[-it]
	}
}
