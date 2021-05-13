package org.hawk.ttc2018;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.hawk.core.query.InvalidQueryException;
import org.eclipse.hawk.core.query.QueryExecutionException;
import org.eclipse.hawk.epsilon.emc.EOLQueryEngine;
import org.eclipse.hawk.graph.updater.GraphModelUpdater;
import org.hawk.ttc2018.metamodels.Metamodels;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Files;

import Changes.ChangesPackage;
import SocialNetwork.SocialNetworkPackage;

/**
 * Base class for the various Hawk-based solutions for TTC18.
 */
public abstract class AbstractLauncher {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractLauncher.class);
	public static final String INITIAL_MODEL_FILENAME = "initial.xmi";

	protected final LauncherOptions opts;
	private final List<Snapshot> results = new ArrayList<>();
	private StandaloneHawk hawk;

	public class Snapshot {
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
			final String msg = String.format("%s;%s;%s;%d;%d;%s;%s;%s", getTool(), opts.getQuery().getIdentifier(),
					opts.getChangeSet() == null ? "" : opts.getChangeSet(), opts.getRunIndex(), iteration, phase.toString(), metric.toString(),
					metricValue + "");

			out.println(msg);
			LOGGER.info(msg);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + Objects.hash(iteration, metric, metricValue, phase);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;

			Snapshot other = (Snapshot) obj;
			return iteration == other.iteration && metric == other.metric
					&& Objects.equals(metricValue, other.metricValue) && phase == other.phase;
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

		public void close() {
			final long elapsedTime = System.nanoTime() - startNanos;
//			final long availableBytes = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

			new Snapshot(iteration, phase, Metric.Time, elapsedTime).print(System.out);
//			System.gc();
//			new Snapshot(iteration, phase, Metric.Memory, availableBytes).print(System.out);
		}
	}

	public AbstractLauncher(LauncherOptions opts) {
		this.opts = opts;
		LOGGER.info("Running in JVM {}", System.getProperty("java.version"));
	}

	public void run() throws Throwable {
		// Make sure the static metamodels are in the global EMF registry
		ChangesPackage.eINSTANCE.getName();
		SocialNetworkPackage.eINSTANCE.getName();

		// Create a backup copy of the initial XMI
		final File fInitial = new File(opts.getChangePath(), INITIAL_MODEL_FILENAME);
		final File tmpBackup = File.createTempFile("backupInitial", ".xmi");
		Files.copy(fInitial, tmpBackup);

		hawk = createHawk();
		try {
			initialization(hawk);
			try (PhaseWrapper w = new PhaseWrapper(0, Phase.Load)) {
				modelLoading(hawk);
			}
			try (PhaseWrapper w = new PhaseWrapper(0, Phase.Initial)) {
				initialView(hawk);
			}

			for (int iChangeSequence = 1; iChangeSequence <= opts.getSequences(); ++iChangeSequence) {
				try (PhaseWrapper w = new PhaseWrapper(iChangeSequence, Phase.Update)) {
					applyChanges(iChangeSequence, hawk);
				}
			}

		} finally {
			hawk.shutdown();

			/*
			 * Reset the input model after we are done (originally at the beginning,
			 * change requested by Georg to avoid interfering with other solutions.
			 *
			 * Use a plain copy instead of depending on 'git checkout', since the Docker
			 * images do not use a git clone.
			 */
			try {
				Files.copy(tmpBackup, fInitial);
				tmpBackup.delete();
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}

	public StandaloneHawk getHawk() {
		return hawk;
	}

	public LauncherOptions getOptions() {
		return opts;
	}

	public List<Snapshot> getResults() {
		return results;
	}

	protected StandaloneHawk createHawk() throws IOException {
		return new StandaloneHawk(new GraphModelUpdater());
	}

	protected void initialView(final StandaloneHawk hawk)
			throws IOException, InvalidQueryException, QueryExecutionException {

		registerDerivedAttribute(hawk);
		final List<List<Object>> results = runQuery(hawk);
		final String elementsString = formatResults(results);

		LOGGER.info("Produced results: {}", results);
		final Snapshot snapshot = new Snapshot(0, Phase.Initial, Metric.Elements, elementsString);
		snapshot.print(System.out);
		this.results.add(snapshot);
	}

	protected String formatResults(final List<List<Object>> results) {
		final StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (List<Object> result : results) {
			if (first) {
				first = false;
			} else {
				sb.append('|');
			}
			sb.append(result.get(0));
		}
		final String elementString = sb.toString();
		return elementString;
	}

	protected abstract List<List<Object>> runQuery(final StandaloneHawk hawk)
			throws IOException, InvalidQueryException, QueryExecutionException;

	protected abstract void modelLoading(final StandaloneHawk hawk) throws Throwable;

	protected void initialization(final StandaloneHawk hawk) throws Exception {
		hawk.run();
		hawk.registerMetamodel(Metamodels.getEcoreMetamodel());
		hawk.registerMetamodel(Metamodels.getSocialMediaMetamodel());
		hawk.registerMetamodel(Metamodels.getChangeSequenceMetamodel());
	}

	protected void applyChanges(int iChangeSequence, final StandaloneHawk hawk)
			throws Throwable, IOException, InvalidQueryException, QueryExecutionException {
		final File changePath = opts.getChangePath();
		final File fChange = new File(changePath, String.format("change%02d.xmi", iChangeSequence));
		final File fInitial = new File(changePath, INITIAL_MODEL_FILENAME);
		applyChanges(fInitial, iChangeSequence, fChange);
	
		hawk.performAndWaitForSync(() -> { hawk.getIndexer().requestImmediateSync(); return null; });
	
		final List<List<Object>> results = runQuery(hawk);
		final String elementsString = formatResults(results);
		LOGGER.info("Produced results: {}", results);
		final Snapshot snapshot = new Snapshot(iChangeSequence, Phase.Update, Metric.Elements, elementsString);
		snapshot.print(System.out);
		this.results.add(snapshot);
	}

	protected abstract void applyChanges(File fInitial, int iChangeSequence, File fChanges) throws Exception;

	protected String getTool() {
		return System.getenv("Tool");
	}

	protected EolModule parseEOLModule(final InputStream is) throws IOException, Exception {
		final EolModule eolm = new EolModule();
		final String applyChangesQuery = streamToString(is);
		eolm.parse(applyChangesQuery);
		eolm.getContext().setModule(eolm);
		return eolm;
	}

	protected String streamToString(final InputStream is) throws IOException {
		final StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
			}
		}
		final String applyChangesQuery = sb.toString();
		return applyChangesQuery;
	}

	protected void registerDerivedAttribute(StandaloneHawk hawk) throws IOException {
		final Query query = opts.getQuery();
		hawk.getIndexer().addDerivedAttribute(SocialNetworkPackage.eNS_URI, query.getExtendedType(), "score",
			"Integer", false, false, false, EOLQueryEngine.TYPE, streamToString(query.getDerivedAttribute()));
	}

}