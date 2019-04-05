package ttc2018;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;

import com.google.common.collect.Ordering;

import Changes.ModelChange;
import Changes.ModelChangeSet;
import comparators.Task1MatchComparator;
import queries.Task1;
import queries.Task1.Match;

public class SolutionQ1 extends Solution {

	private Task1.Matcher matcher;
	private Task1MatchComparator comparator = new Task1MatchComparator();
	
	@Override
	public String Initial() {
		matcher = engine.getMatcher(Task1.instance());
		return query();	
	}

	@Override
	public String Update(ModelChangeSet changes) {
		EList<ModelChange> coll = changes.getChanges();
		for (ModelChange change : coll) {
			change.apply();
		}
		return query();
	}

	private String query() {
		Collection<Match> matches = matcher.getAllMatches();
		List<Match> top3Matches = Ordering.from(comparator).reverse().sortedCopy(matches).subList(0, 3);
		String top3Ids = top3Matches.stream().map(m -> m.getPost().getId()).collect(Collectors.joining("|"));		
		return top3Ids;
	}

}
