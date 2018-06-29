package ttc2018

import Changes.ModelChange
import Changes.ModelChangeSet
import SocialNetwork.Comment
import SocialNetwork.Post
import java.util.Comparator
import java.util.stream.Collector
import org.eclipse.emf.common.util.EList

class SolutionQ1 extends Solution {
	
	public final static int COMMENT_SCORE = 10
	
	public final static int LIKE_SCORE = 1
	
	override String Initial() {
		return queryQ1
	}

	override String Update(ModelChangeSet changes) {
		var EList<ModelChange> coll = changes.getChanges()
		for (ModelChange change : coll) {
			change.apply()
		}
		return queryQ1
	}

	def private queryQ1() {
		val socialNetwork = this.socialNetwork
		val posts = socialNetwork.posts

		val answer = posts
		.parallelStream
		.collect(Collector.of([
			new Greatest3(
				Comparator.comparingInt[
					if(it === null) {
						Integer.MIN_VALUE
					} else {
						computeScore	// TODO: cache score?
					}
				]
					.thenComparing(Comparator.comparing[
						timestamp	// TODO: make null-safe?
					])
			)
		], [$0.add($1)], [$0.merge($1)], [asList])).map[
			id
		]
		.join("|")
		
		return answer
	}

	def private computeScore(Post p) {
		val comments = p.eAllContents.filter(Comment).toList
		val score = comments.size * COMMENT_SCORE
					+ comments
						.parallelStream
						.mapToInt[
							likedBy.size
						].sum * LIKE_SCORE

		return score
	}
}
