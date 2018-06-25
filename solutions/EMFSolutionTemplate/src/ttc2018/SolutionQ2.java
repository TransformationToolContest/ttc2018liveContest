package ttc2018;

import org.eclipse.emf.common.util.EList;

import Changes.ModelChange;
import Changes.ModelChangeSet;

public class SolutionQ2 extends Solution {

	@Override
	public String Initial() {
		// TODO Implement Q2
		return null;
	}

	@Override
	public String Update(ModelChangeSet changes) {
		EList<ModelChange> coll = changes.getChanges();
		for (ModelChange change : coll) {
			change.apply();
		}
		// TODO Implement Q2
		return null;
	}

}
