package org.hawk.ttc2018;

import java.io.InputStream;

import org.hawk.ttc2018.queries.EOLQueries;

public enum Query {
	Controversial {
		@Override
		public InputStream getQuery() {
			return EOLQueries.getControversialPostsQuery();
		}

		@Override
		public String getIdentifier() {
			return CONTROVERSIAL_ID;
		}
	}, Influential {
		@Override
		public InputStream getQuery() {
			return EOLQueries.getInfluentialCommentsQuery();
		}

		@Override
		public String getIdentifier() {
			return INFLUENTIAL_ID;
		}
	};

	public static final String INFLUENTIAL_ID = "Q2";
	public static final String CONTROVERSIAL_ID = "Q1";

	public abstract InputStream getQuery();
	public abstract String getIdentifier();

	public static Query fromQuery(String id) {
		switch (id) {
		case CONTROVERSIAL_ID:
			return Controversial;
		case INFLUENTIAL_ID:
			return Influential;
		default:
			throw new IllegalArgumentException("Unknown query " + id);
		}
	}
}