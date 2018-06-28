package org.hawk.ttc2018.queries;

import java.util.Comparator;
import java.util.List;

/**
 * Comparator that sorts results by descending score, and resolves ties
 * by descending timestamps (from newer to older).
 */
public class ResultComparator implements Comparator<List<Object>> {

	@Override
	public int compare(List<Object> o1, List<Object> o2) {
		int cmpScores = Integer.compare((Integer)o1.get(1), (Integer)o2.get(1));
		if (cmpScores != 0) {
			// Descending order on scores
			return -cmpScores;
		}

		/*
		 * Resolve ties by timestamp (most recent first - lexicographical ordering is
		 * fine since it's ISO 8601).
		 */
		return -((String)o1.get(2)).compareTo((String)o2.get(2));
	}

}
