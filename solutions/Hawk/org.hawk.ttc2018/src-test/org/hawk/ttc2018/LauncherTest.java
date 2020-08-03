package org.hawk.ttc2018;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.hawk.ttc2018.AbstractLauncher.Snapshot;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LauncherTest {

	private static final int N_SEQUENCES = 20;

	private PrintStream oldErr;
	private LauncherOptions opts;

	@Before
	public void setUp() throws IOException {
		oldErr = System.err;
		System.setErr(new PrintStream(new FileOutputStream("stderr.txt")));

		opts = new LauncherOptions();
		opts.setRunIndex(0);
		opts.setSequences(N_SEQUENCES);
	}

	@After
	public void tearDown() {
		System.setErr(oldErr);
	}

	@Test
	public void batchQ1C1() throws Throwable {
		testQuery(BatchLauncher::new, "1", Query.Controversial);
	}

	@Test
	public void batchQ2C1() throws Throwable {
		testQuery(BatchLauncher::new, "1", Query.Influential);
	}

	@Test
	public void incUpdateQ1C1() throws Throwable {
		testQuery(IncrementalUpdateLauncher::new, "1", Query.Controversial);
	}

	@Test
	public void incUpdateQ2C1() throws Throwable {
		testQuery(IncrementalUpdateLauncher::new, "1", Query.Influential);
	}

	@Test
	public void incUpdateQueryQ1C1() throws Throwable {
		testQuery(IncrementalUpdateQueryLauncher::new, "1", Query.Controversial);
	}

	@Test
	public void incUpdateQueryQ2C1() throws Throwable {
		testQuery(IncrementalUpdateQueryLauncher::new, "1", Query.Influential);
	}

	private void testQuery(Function<LauncherOptions, AbstractLauncher> ctor, String changeSet, Query query) throws Throwable {
		final File fChangeSet = new File("../../../models/" + changeSet);
		opts.setChangePath(fChangeSet.getCanonicalFile());
		opts.setChangeSet(changeSet);
		opts.setQuery(query);

		AbstractLauncher launcher = ctor.apply(opts);
		launcher.run();
		assertEquals(readExpected(launcher), launcher.getResults());
	}

	private List<Snapshot> readExpected(AbstractLauncher launcher) throws IOException {
		final List<Snapshot> l = new ArrayList<>();

		try (Reader reader = Files.newBufferedReader(Paths.get("../../../expected-results/results.csv"));
				CSVParser csvParser = new CSVParser(reader, CSVFormat.EXCEL.withDelimiter(';'))) {

			for (CSVRecord csvRecord : csvParser) {
				final String query = csvRecord.get(0);
				if (!opts.getQuery().getIdentifier().equals(query)) {
					continue;
				}
				
				final String changeSet = csvRecord.get(1);
				if (!opts.getChangeSet().equals(changeSet)) {
					continue;
				}

				final int iteration = Integer.valueOf(csvRecord.get(2));
				final String phaseName = csvRecord.get(3);
				final String sResults = csvRecord.get(4);

				Snapshot snapshot = launcher.new Snapshot(iteration, Phase.valueOf(phaseName), Metric.Elements, sResults);
				l.add(snapshot);
			}
		}
	
		return l;
	}
}
