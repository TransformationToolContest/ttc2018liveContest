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

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.eclipse.hawk.core.query.InvalidQueryException;
import org.eclipse.hawk.core.query.QueryExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Alternative implementation that takes a change sequence model and uses it to
 * update the graph directly, understanding a specific change sequence model
 * format.
 */
public class IncrementalUpdateLauncher extends AbstractIncrementalUpdateLauncher {

	private static final Logger LOGGER = LoggerFactory.getLogger(IncrementalUpdateLauncher.class);

	public IncrementalUpdateLauncher(LauncherOptions opts) {
		super(opts);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<List<Object>> runQuery(StandaloneHawk hawk)
			throws IOException, InvalidQueryException, QueryExecutionException {
		return (List<List<Object>>) hawk.eol(opts.getQuery().getDerivedQuery());
	}

	@Override
	protected void initialization(StandaloneHawk hawk) throws Exception {
		super.initialization(hawk);
		registerDerivedAttribute(hawk);
	}

	public static void main(String[] args) {
		Map<String, String> env = System.getenv();
		try {
			new IncrementalUpdateLauncher(new LauncherOptions(env)).run();
		} catch (Throwable e) {
			LOGGER.error(e.getMessage(), e);
		}
	}


	@Override
	protected String getTool() {
		String parent = super.getTool();
		return parent != null ? parent : "HawkIncUpdate";
	}

}
