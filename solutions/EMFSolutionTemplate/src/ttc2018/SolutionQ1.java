package ttc2018;

import org.eclipse.emf.common.util.EList;

import Changes.ModelChange;
import Changes.ModelChangeSet;

public class SolutionQ1 extends Solution {

	@Override
	public String Initial() {
		// TODO Implement Q1
		return null;
	}

	@Override
	public String Update(ModelChangeSet changes) {
		EList<ModelChange> coll = changes.getChanges();
		for (ModelChange change : coll) {
			change.apply();
		}
		// TODO Implement Q1
		return null;
	}

}
