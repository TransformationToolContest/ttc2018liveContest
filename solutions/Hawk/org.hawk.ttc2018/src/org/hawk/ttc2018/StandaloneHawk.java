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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

import org.hawk.core.IConsole;
import org.hawk.core.IModelIndexer;
import org.hawk.core.IModelIndexer.ShutdownRequestType;
import org.hawk.core.IModelUpdater;
import org.hawk.core.graph.IGraphDatabase;
import org.hawk.core.query.IQueryEngine;
import org.hawk.core.query.InvalidQueryException;
import org.hawk.core.query.QueryExecutionException;
import org.hawk.core.runtime.ModelIndexerImpl;
import org.hawk.core.security.FileBasedCredentialsStore;
import org.hawk.core.util.SLF4JConsole;
import org.hawk.emf.metamodel.EMFMetaModelResourceFactory;
import org.hawk.emf.model.EMFModelResourceFactory;
import org.hawk.epsilon.emc.EOLQueryEngine;
import org.hawk.graph.updater.GraphMetaModelUpdater;
import org.hawk.localfolder.LocalFile;
import org.hawk.localfolder.LocalFolder;
import org.hawk.neo4j_v2.Neo4JDatabase;

/**
 * Simple abstraction of a standalone Hawk indexer, to be run from a plain Java
 * program. Based on the JUnit test suite.
 */
public class StandaloneHawk {

	private final File indexFolder;

	private IConsole console;
	private IGraphDatabase db;
	private IModelIndexer indexer;
	private IQueryEngine queryEngine;
	private IModelUpdater updater;

	public StandaloneHawk(IModelUpdater updater) throws IOException {
		this.indexFolder = Files.createTempDirectory("hawkttc18").toFile();
		this.updater = updater;
	}

	public void run() throws Exception {
		console = new SLF4JConsole();
		db = new Neo4JDatabase();
		db.run(new File(indexFolder, "db"), console);
		
		final FileBasedCredentialsStore credStore = new FileBasedCredentialsStore(
			new File("keystore"), "admin".toCharArray());

		indexer = new ModelIndexerImpl("ttc18", indexFolder, credStore, console);
		indexer.addMetaModelResourceFactory(new EMFMetaModelResourceFactory());
		indexer.addModelResourceFactory(new IntrinsicIDEMFModelResourceFactory());

		queryEngine = new EOLQueryEngine();
		indexer.addQueryEngine(queryEngine);
		indexer.setMetaModelUpdater(new GraphMetaModelUpdater());
		indexer.addModelUpdater(updater);
		indexer.setDB(db, true);
		indexer.init(0, 0);
	}

	public void shutdown() throws Exception {
		if (indexer != null) {
			indexer.shutdown(ShutdownRequestType.ALWAYS);
		}
		if (db != null) {
			db.delete();
		}
	}

	public void registerMetamodel(InputStream is) throws Exception {
		Path tmpFile = Files.createTempFile("hawkmm", ".ecore");
		tmpFile.toFile().delete();
		Files.copy(is, tmpFile);
		indexer.registerMetamodels(tmpFile.toFile());
		tmpFile.toFile().delete();
	}

	public LocalFile requestFileIndex(final File file) throws Exception {
		final LocalFile vcs = new LocalFile();
		vcs.init(file.getAbsolutePath(), indexer);
		vcs.run();
		indexer.addVCSManager(vcs, true);
		return vcs;
	}

	public LocalFolder requestFolderIndex(File folder, Function<File, Boolean> fileFilter) throws Exception {
		final LocalFolder vcs = new LocalFolder();
		vcs.init(folder.getAbsolutePath(), indexer);
		vcs.setFileFilter(fileFilter);
		vcs.run();
		indexer.addVCSManager(vcs, true);
		return vcs;
	}

	public Object eol(final String eolQuery) throws InvalidQueryException, QueryExecutionException {
		return eol(eolQuery, null);
	}

	public Object eol(final String eolQuery, Map<String, Object> context) throws InvalidQueryException, QueryExecutionException {
		return queryEngine.query(indexer, eolQuery, context);
	}

	public void waitForSync(final Callable<?> r) throws Throwable {
		final Semaphore sem = new Semaphore(0);
		final SyncEndListener changeListener = new SyncEndListener(r, sem);
		indexer.addGraphChangeListener(changeListener);
		if (!sem.tryAcquire(600, TimeUnit.SECONDS)) {
			throw new TimeoutException("Synchronization timed out");
		} else {
			indexer.removeGraphChangeListener(changeListener);
			if (changeListener.getThrowable() != null) {
				throw changeListener.getThrowable();
			}
		}
	}

	public void waitForSync() throws Throwable {
		waitForSync(() -> { return null; });
	}

	public Object eol(InputStream is) throws IOException, InvalidQueryException, QueryExecutionException {
		final StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
			}
		}
		final String query = sb.toString();

		return eol(query);
	}

	public IModelIndexer getIndexer() {
		return indexer;
	}

}
