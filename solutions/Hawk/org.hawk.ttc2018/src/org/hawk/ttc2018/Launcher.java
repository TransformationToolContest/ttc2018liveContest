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
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.models.ModelRepository;
import org.hawk.core.query.InvalidQueryException;
import org.hawk.core.query.QueryExecutionException;
import org.hawk.ttc2018.metamodels.Metamodels;
import org.hawk.ttc2018.queries.EOLQueries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Runs an instance of the TTC 2018 benchmark for a solution based on Hawk. Uses
 * a naive implementation that reindexes the file after every change sequence
 * and reruns the query.
 */
public class Launcher {

	private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

	private static final String INITIAL_MODEL_FILENAME = "initial.xmi";
	private static final String SOCIAL_MMURI = "https://www.transformation-tool-contest.eu/2018/social_media";
	private static final String CHANGES_MMURI = "http://nmf.codeplex.com/changes";

	private final File changePath;
	private final String changeSet;
	private final Query query;
	private final int runIndex, sequences;

	public class Snapshot {
		public final String tool = "Hawk";
		public int iteration;
		public final Phase phase;
		public final Metric metric;
		public final Object metricValue;

		public Snapshot(int iteration, Phase phase, Metric metric, Object value) {
			this.iteration = iteration;
			this.phase = phase;
			this.metric = metric;
			this.metricValue = value;
		}

		public void print(PrintStream out) {
			final String msg = String.format("%s,%s,%s,%d,%d,%s,%s,%s",
				tool, query.getIdentifier(), changeSet == null ? "" : changeSet,
				runIndex, iteration, phase.toString(),
				metric.toString(), metricValue + "");

			out.println(msg);
			LOGGER.info(msg);
		}
	}

	/**
	 * Wraps over a non-update phase or an update after a change sequence, printing
	 * upon close the memory and time usage in bytes and nanoseconds, respectively.
	 */
	public class PhaseWrapper implements Closeable {
		private final long startNanos;
		private final int iteration;
		private final Phase phase;

		public PhaseWrapper(int iteration, Phase phase) {
			this.startNanos = System.nanoTime();
			this.iteration = iteration;
			this.phase = phase;
		}

		public void close() throws IOException {
			final long elapsedTime = System.nanoTime() - startNanos;
			final long availableBytes = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

			new Snapshot(iteration, phase, Metric.Time, elapsedTime).print(System.out);
			System.gc();
			new Snapshot(iteration, phase, Metric.Memory, availableBytes).print(System.out);
		}
	}

	public static void printHeader(PrintStream out) {
		final String header = "Tool,View,ChangeSet,RunIndex,Iteration,PhaseName,MetricName,MetricValue";
		out.println(header);
		LOGGER.info(header);
	}

	public Launcher(Map<String, String> env) {
		this.changePath = new File(env.get("ChangePath"));
		this.changeSet = env.get("ChangeSet");
		this.query = Query.fromQuery(env.get("Query"));
		this.runIndex = Integer.valueOf(env.get("RunIndex"));
		this.sequences = Integer.valueOf(env.get("Sequences"));
	}

	public void run() throws Throwable {
		final StandaloneHawk hawk = new StandaloneHawk();
		try {
			printHeader(System.out);

			try (PhaseWrapper w = new PhaseWrapper(0, Phase.Initialization)) {
				initialization(hawk);
			}
			try (PhaseWrapper w = new PhaseWrapper(0, Phase.Loading)) {
				modelLoading(hawk);
			}
			try (PhaseWrapper w = new PhaseWrapper(0, Phase.Initial)) {
				initialView(hawk);
			}

			for (int iChangeSequence = 1; iChangeSequence <= sequences; ++iChangeSequence) {
				try (PhaseWrapper w = new PhaseWrapper(iChangeSequence, Phase.Updates)) {
					applyChanges(iChangeSequence, hawk);
				}
			}

		} finally {
			hawk.shutdown();
		}
	}

	protected void applyChanges(int iChangeSequence, final StandaloneHawk hawk)
			throws Throwable, IOException, InvalidQueryException, QueryExecutionException {
		final File fChange = new File(changePath, String.format("change%02d.xmi", iChangeSequence));
		final File fInitial = new File(changePath, INITIAL_MODEL_FILENAME);
		applyChanges(fInitial, fChange);

		hawk.requestSync();
		hawk.waitForSync();

		@SuppressWarnings("unchecked")
		List<List<Integer>> results = (List<List<Integer>>) hawk.eol(query.getQuery());
		new Snapshot(iChangeSequence, Phase.Updates, Metric.Elements, '"' + results.toString() + '"').print(System.out);
	}

	private void applyChanges(File fInitial, File fChanges) throws Exception {
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
		emfChanges.setMetamodelUris(Arrays.asList(SOCIAL_MMURI, CHANGES_MMURI));
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

	protected void initialView(final StandaloneHawk hawk)
			throws IOException, InvalidQueryException, QueryExecutionException {
		@SuppressWarnings("unchecked")
		List<List<Integer>> results = (List<List<Integer>>) hawk.eol(query.getQuery());
		new Snapshot(0, Phase.Initial, Metric.Elements, '"' + results.toString() + '"').print(System.out);
	}

	protected void modelLoading(final StandaloneHawk hawk) throws Exception, Throwable {
		hawk.requestFileIndex(new File(changePath, INITIAL_MODEL_FILENAME));
		hawk.waitForSync();
	}

	protected void initialization(final StandaloneHawk hawk) throws Exception {
		hawk.run();
		hawk.registerMetamodel(Metamodels.getEcoreMetamodel());
		hawk.registerMetamodel(Metamodels.getSocialMediaMetamodel());
		hawk.registerMetamodel(Metamodels.getChangeSequenceMetamodel());
	}

	public static void main(String[] args) {
		Map<String, String> env = System.getenv();
		try {
			new Launcher(env).run();
		} catch (Throwable e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
}
