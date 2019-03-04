package comparators;

import java.util.Comparator;

import com.google.common.collect.ComparisonChain;

import queries.Task1;
import queries.Task1.Match;

public class Task1MatchComparator implements Comparator<Task1.Match> {

	@Override
	public int compare(Match m1, Match m2) {
		return ComparisonChain.start()
				.compare(m1.getScore(), m2.getScore())
				.compare(m1.getPost().getTimestamp(), m2.getPost().getTimestamp())
				.result();
	}

}
