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

	public static InputStream getControversialPostsDerivedAttribute() {
		return EOLQueries.class.getResourceAsStream("q1-derivedAttribute.eol");
	}

	public static InputStream getControversialPostsDerivedQuery() {
		return EOLQueries.class.getResourceAsStream("q1-derivedAttributeQuery.eol");
	}

	public static InputStream getInfluentialCommentsQuery() {
		return EOLQueries.class.getResourceAsStream("q2.eol");
	}

	public static InputStream getInfluentialCommentsDerivedAttribute() {
		return EOLQueries.class.getResourceAsStream("q2-derivedAttribute.eol");
	}

	public static InputStream getInfluentialCommentsDerivedQuery() {
		return EOLQueries.class.getResourceAsStream("q2-derivedAttributeQuery.eol");
	}

}
