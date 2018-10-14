package comparators;

import java.util.Comparator;

import com.google.common.collect.ComparisonChain;

import queries.Task2;
import queries.Task2.Match;

public class Task2MatchComparator implements Comparator<Task2.Match> {

	@Override
	public int compare(Match m1, Match m2) {
		return ComparisonChain.start()
				.compare(m1.getScore(), m2.getScore())
				.compare(m1.getComment().getTimestamp(), m2.getComment().getTimestamp())
				.result();
	}

}
