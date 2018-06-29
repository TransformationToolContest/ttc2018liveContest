package ttc2018;

import org.eclipse.emf.common.util.EList;

import Changes.ModelChange;
import Changes.ModelChangeSet;

public class SolutionQ2 extends Solution {

	@Override
	public String Initial() {
		return SolutionQ1.run("Q2", this.getSocialNetwork());
	}

	@Override
	public String Update(ModelChangeSet changes) {
		EList<ModelChange> coll = changes.getChanges();
		for (ModelChange change : coll) {
			change.apply();
		}
		return Initial();
	}
}
