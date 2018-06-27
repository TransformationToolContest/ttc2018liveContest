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

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.models.ModelRepository;
import org.hawk.ttc2018.queries.EOLQueries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Changes.ChangesPackage;
import SocialNetwork.SocialNetworkPackage;


/**
 * Runs an instance of the TTC 2018 benchmark for a solution based on Hawk. Uses
 * a naive implementation that reindexes the file after every change sequence
 * and reruns the query.
 */
public class BatchLauncher extends AbstractLauncher {

	private static final Logger LOGGER = LoggerFactory.getLogger(BatchLauncher.class);

	public BatchLauncher(Map<String, String> env) {
		super(env);
	}

	@Override
	protected void applyChanges(File fInitial, int iChangeSequence, File fChanges) throws Exception {
		final EolModule eolm = new EolModule();

		final StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(EOLQueries.getApplyChangesQuery()))) {
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
			}
		}
		final String applyChangesQuery = sb.toString();
		eolm.parse(applyChangesQuery);

		final ModelRepository repo = eolm.getContext().getModelRepository();
		final EmfModel emfChanges = new EmfModel();
		emfChanges.setMetamodelUris(Arrays.asList(SocialNetworkPackage.eNS_URI, ChangesPackage.eNS_URI));
		emfChanges.setModelFile(fChanges.getCanonicalPath());
		emfChanges.setName("Changes");
		emfChanges.setReadOnLoad(true);
		emfChanges.setStoredOnDisposal(false);
		emfChanges.setExpand(true);
		emfChanges.load();
		repo.addModel(emfChanges);

		eolm.execute();

		// Save the changed model, but not the change sequence!
		for (Resource r : emfChanges.getResource().getResourceSet().getResources()) {
			if (r.getURI().path().endsWith("/" + INITIAL_MODEL_FILENAME)) {
				r.save(null);
			}
		}

		eolm.getContext().getModelRepository().dispose();
		eolm.getContext().dispose();
	}

	@Override
	protected void modelLoading(final StandaloneHawk hawk) throws Throwable {
		hawk.requestFileIndex(new File(changePath, INITIAL_MODEL_FILENAME));
		hawk.waitForSync();
	}

	public static void main(String[] args) {
		Map<String, String> env = System.getenv();
		try {
			new BatchLauncher(env).run();
		} catch (Throwable e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
}
