package ttc2018

import Changes.ModelChange
import Changes.ModelChangeSet
import SocialNetwork.Comment
import SocialNetwork.User
import de.fzi.se.Layering
import java.util.Collection
import java.util.Comparator
import java.util.concurrent.ConcurrentHashMap
import java.util.stream.Collector
import org.eclipse.emf.common.util.EList

class SolutionQ2 extends Solution {
	override String Initial() {
		return queryQ2
	}

	override String Update(ModelChangeSet changes) {
		var EList<ModelChange> coll = changes.getChanges()
		for (ModelChange change : coll) {
			change.apply()
		}
		return queryQ2
	}
	

	def private queryQ2() {
		val comments = socialNetwork.eAllContents.filter(Comment).toList

		val scoreCache = new ConcurrentHashMap<Comment, Integer>
		val answer = comments
				.parallelStream	
				.collect(Collector.of([
							new Greatest3(
								Comparator.comparingInt[
									if(it === null) {
										Integer.MIN_VALUE
									} else {
										scoreCache.computeIfAbsent(it)[computeScore]
									}
								]
									.thenComparing(Comparator.comparing[
										timestamp	// TODO: make null-safe?
									])
							)
						], [$0.add($1)], [$0.merge($1)], [asList])).map[id]
						.join("|")
		return answer
	}

	def <E> intersection(Collection<E> a, Collection<E> b) {
		val ret = a.toSet
		ret.retainAll(b.toSet)
		ret
	}

	def private computeScore(Comment c) {
		val layering = new Layering[User u |
			u.friends.intersection(c.likedBy)
		]

		val comps = layering.CreateLayers(c.likedBy)
		val score = comps.map[size*size].reduce[$0+$1] ?: 0

		return score
	}
}
