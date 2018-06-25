package org.hawk.ttc2018.queries;

import java.io.InputStream;

public final class EOLQueries {

	private EOLQueries() {}

	public static InputStream getApplyChangesQuery() {
		return EOLQueries.class.getResourceAsStream("applyChanges.eol");
	}

	public static InputStream getControversialPostsQuery() {
		return EOLQueries.class.getResourceAsStream("q1.eol");
	}

	public static InputStream getInfluentialCommentsQuery() {
		return EOLQueries.class.getResourceAsStream("q2.eol");
	}

}
