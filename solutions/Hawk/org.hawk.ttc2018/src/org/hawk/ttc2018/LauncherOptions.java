package org.hawk.ttc2018;

import java.io.File;
import java.util.Map;

public class LauncherOptions {

	protected File changePath;
	protected String changeSet;
	protected Query query;
	protected int runIndex;
	protected int sequences;

	public LauncherOptions() {
		// use setters - for testing
	}

	public LauncherOptions(Map<String, String> env) {
		this.changePath = new File(env.get("ChangePath"));
		this.changeSet = env.get("ChangeSet");
		this.query = Query.fromQuery(env.get("Query"));
		this.runIndex = Integer.valueOf(env.get("RunIndex"));
		this.sequences = Integer.valueOf(env.get("Sequences"));
	}

	public File getChangePath() {
		return changePath;
	}

	public void setChangePath(File changePath) {
		this.changePath = changePath;
	}

	public String getChangeSet() {
		return changeSet;
	}

	public void setChangeSet(String changeSet) {
		this.changeSet = changeSet;
	}

	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}

	public int getRunIndex() {
		return runIndex;
	}

	public void setRunIndex(int runIndex) {
		this.runIndex = runIndex;
	}

	public int getSequences() {
		return sequences;
	}

	public void setSequences(int sequences) {
		this.sequences = sequences;
	}

}
