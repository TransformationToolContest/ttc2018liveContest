package org.hawk.ttc2018;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import org.hawk.core.query.InvalidQueryException;
import org.hawk.core.query.QueryExecutionException;
import org.hawk.graph.updater.GraphModelUpdater;
import org.hawk.ttc2018.metamodels.Metamodels;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Changes.ChangesPackage;
import SocialNetwork.SocialNetworkPackage;

/**
 * Base class for the various Hawk-based solutions for TTC18.
 */
public abstract class AbstractLauncher {

	private static final Logger LOGGER = LoggerFactory.getLogger(BatchLauncher.class);
	public static final String INITIAL_MODEL_FILENAME = "initial.xmi";

	protected final File changePath;
	protected final String changeSet;
	protected final Query query;
	protected final int runIndex;
	protected final int sequences;

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
			final String msg = String.format("%s,%s,%s,%d,%d,%s,%s,%s", tool, query.getIdentifier(),
					changeSet == null ? "" : changeSet, runIndex, iteration, phase.toString(), metric.toString(),
					metricValue + "");

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

	public AbstractLauncher(Map<String, String> env) {
		this.changePath = new File(env.get("ChangePath"));
		this.changeSet = env.get("ChangeSet");
		this.query = Query.fromQuery(env.get("Query"));
		this.runIndex = Integer.valueOf(env.get("RunIndex"));
		this.sequences = Integer.valueOf(env.get("Sequences"));
	}

	public void run() throws Throwable {
		// Make sure the static metamodels are in the global EMF registry
		ChangesPackage.eINSTANCE.getName();
		SocialNetworkPackage.eINSTANCE.getName();

		final StandaloneHawk hawk = createHawk();
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

	protected StandaloneHawk createHawk() throws IOException {
		return new StandaloneHawk(new GraphModelUpdater());
	}

	protected void initialView(final StandaloneHawk hawk)
			throws IOException, InvalidQueryException, QueryExecutionException {
		@SuppressWarnings("unchecked")
		List<List<Integer>> results = (List<List<Integer>>) hawk.eol(query.getQuery());
		new Snapshot(0, Phase.Initial, Metric.Elements, '"' + results.toString() + '"').print(System.out);
	}

	protected abstract void modelLoading(final StandaloneHawk hawk) throws Throwable;

	protected void initialization(final StandaloneHawk hawk) throws Exception {
		hawk.run();
		hawk.registerMetamodel(Metamodels.getEcoreMetamodel());
		hawk.registerMetamodel(Metamodels.getSocialMediaMetamodel());
		hawk.registerMetamodel(Metamodels.getChangeSequenceMetamodel());
	}

	protected void applyChanges(int iChangeSequence, final StandaloneHawk hawk)
			throws Throwable, IOException, InvalidQueryException, QueryExecutionException {
		final File fChange = new File(changePath, String.format("change%02d.xmi", iChangeSequence));
		final File fInitial = new File(changePath, INITIAL_MODEL_FILENAME);
		applyChanges(fInitial, iChangeSequence, fChange);
	
		hawk.requestSync();
		hawk.waitForSync();
	
		@SuppressWarnings("unchecked")
		List<List<Integer>> results = (List<List<Integer>>) hawk.eol(query.getQuery());
		new Snapshot(iChangeSequence, Phase.Updates, Metric.Elements, '"' + results.toString() + '"').print(System.out);
	}

	protected abstract void applyChanges(File fInitial, int iChangeSequence, File fChanges) throws Exception;

}