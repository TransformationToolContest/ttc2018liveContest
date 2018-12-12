package ttc2018;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;

import com.google.common.collect.Ordering;

import Changes.ModelChange;
import Changes.ModelChangeSet;
import comparators.Task2MatchComparator;
import queries.Task2;
import queries.Task2.Match;

public class SolutionQ2 extends Solution {

	private Task2.Matcher matcher;
	private Task2MatchComparator comparator = new Task2MatchComparator();
	
	@Override
	public String Initial() {
		matcher = engine.getMatcher(Task2.instance());
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
		String top3Ids = top3Matches.stream().map(m -> m.getComment().getId() + "--" + m.getScore()).collect(Collectors.joining("|"));	
		return top3Ids;
	}
	
}
