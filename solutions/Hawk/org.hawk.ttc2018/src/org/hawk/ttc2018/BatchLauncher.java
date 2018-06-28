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
import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
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
		final EolModule eolm = parseEOLModule(EOLQueries.getApplyChangesQuery());

		final ModelRepository repo = eolm.getContext().getModelRepository();

		// Epsilon's EmfModel doesn't have a way to provide custom EMF load options
		final ResourceSet rs = new ResourceSetImpl();
		rs.getPackageRegistry().put(ChangesPackage.eNS_URI, ChangesPackage.eINSTANCE);
		rs.getPackageRegistry().put(SocialNetworkPackage.eNS_URI, SocialNetworkPackage.eINSTANCE);
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new IntrinsicIDXMIResourceFactoryImpl());

		final Map<String, Boolean> loadOptions = Collections.singletonMap(XMIResource.OPTION_DEFER_IDREF_RESOLUTION, true);
		final XMIResourceImpl emfInitialModel = (XMIResourceImpl) rs.createResource(URI.createFileURI(fInitial.getAbsolutePath()));
		emfInitialModel.load(loadOptions);
		final XMIResourceImpl emfChangesModel = (XMIResourceImpl) rs.createResource(URI.createFileURI(fChanges.getAbsolutePath()));
		emfChangesModel.load(loadOptions);

		final InMemoryEmfModel epsilonEMFInitialModel = new InMemoryEmfModel(emfInitialModel);
		epsilonEMFInitialModel.setName("Model");
		repo.addModel(epsilonEMFInitialModel);
		final InMemoryEmfModel epsilonEMFChangesModel = new InMemoryEmfModel(emfChangesModel);
		epsilonEMFChangesModel.setName("Changes");
		repo.addModel(epsilonEMFChangesModel);

		eolm.execute();
		
		emfInitialModel.save(null);
		emfInitialModel.unload();
		emfChangesModel.unload();

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

	@Override
	protected String getTool() {
		return "Hawk";
	}
}
