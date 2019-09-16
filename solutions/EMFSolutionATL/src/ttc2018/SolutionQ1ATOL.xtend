package ttc2018;

import Changes.ModelChange
import Changes.ModelChangeSet
import org.eclipse.papyrus.aof.core.IBox

class SolutionQ1ATOL extends Solution {

	var IBox<String> result

	override Initial() {
		result = new Q1()._topPosts(socialNetwork)
		result.join("|")
	}

	override Update(ModelChangeSet changes) {
		val coll = changes.changes
		for (ModelChange change : coll) {
			change.apply
		}
		return result.join("|")
	}

}
