/*******************************************************************************
 * Copyright (c) 2018 Aston University.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License, v. 2.0 are satisfied: GNU General Public License, version 3.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-3.0
 *
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
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