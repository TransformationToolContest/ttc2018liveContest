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

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hawk.core.IModelIndexer;
import org.hawk.localfolder.LocalFolder;
import org.hawk.ttc2018.updaters.ChangeSequenceAwareUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import SocialNetwork.SocialNetworkPackage;


/**
 * Alternative implementation that takes a change sequence model and uses it to
 * update the graph directly, understanding a specific change sequence model
 * format.
 */
public class IncrementalUpdateLauncher extends AbstractLauncher {

	private static final Logger LOGGER = LoggerFactory.getLogger(IncrementalUpdateLauncher.class);
	private static final Pattern CHANGES_FNAME = Pattern.compile("change0*([0-9]+).xmi");

	private LocalFolder localFolder;

	public IncrementalUpdateLauncher(Map<String, String> env) {
		super(env);
	}

	@Override
	protected void applyChanges(File fInitial, int changeSequenceLimit, File fChanges) throws Exception {
		localFolder.setFileFilter(filterByChangeSequenceLimit(changeSequenceLimit));
	}

	protected Function<File, Boolean> filterByChangeSequenceLimit(int changeSequenceLimit) {
		return (f) -> {
			if (AbstractLauncher.INITIAL_MODEL_FILENAME.equals(f.getName())) {
				return true;
			} else {
				Matcher matcher = CHANGES_FNAME.matcher(f.getName());
				if (matcher.matches()) {
					final int iChangeSequence = Integer.valueOf(matcher.group(1));
					return iChangeSequence <= changeSequenceLimit;
				} else {
					return true;
				}
			}
		};
	}

	@Override
	protected void modelLoading(final StandaloneHawk hawk) throws Throwable {
		localFolder = hawk.requestFolderIndex(changePath);
		localFolder.setFileFilter(filterByChangeSequenceLimit(0));
		hawk.waitForSync();

		// Need these for quickly finding by ID
		final IModelIndexer indexer = hawk.getIndexer();
		indexer.addIndexedAttribute(SocialNetworkPackage.eNS_URI, "Post", "id");
		indexer.addIndexedAttribute(SocialNetworkPackage.eNS_URI, "Comment", "id");
		indexer.addIndexedAttribute(SocialNetworkPackage.eNS_URI, "User", "id");
	}

	@Override
	protected StandaloneHawk createHawk() throws IOException {
		return new StandaloneHawk(new ChangeSequenceAwareUpdater());
	}

	public static void main(String[] args) {
		Map<String, String> env = System.getenv();
		try {
			new IncrementalUpdateLauncher(env).run();
		} catch (Throwable e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	@Override
	protected String getTool() {
		return "HawkIncUpdate";
	}
	
}
