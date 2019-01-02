package ttc2018;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Level;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.viatra.query.runtime.api.ViatraQueryEngine;
import org.eclipse.viatra.query.runtime.api.ViatraQueryEngineOptions;
import org.eclipse.viatra.query.runtime.emf.EMFScope;
import org.eclipse.viatra.query.runtime.matchers.backend.QueryEvaluationHint;
import org.eclipse.viatra.query.runtime.matchers.backend.QueryHintOption;
import org.eclipse.viatra.query.runtime.rete.matcher.ReteBackendFactory;
import org.eclipse.viatra.query.runtime.rete.util.ReteHintOptions;
import org.eclipse.viatra.query.runtime.util.ViatraQueryLoggingUtil;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import Changes.ModelChangeSet;
import SocialNetwork.SocialNetworkRoot;

public abstract class Solution {
	protected SocialNetworkRoot socialNetwork;
	protected ResourceSet resourceSet;
	protected EMFScope scope;
	protected ViatraQueryEngine engine;
	
    public SocialNetworkRoot getSocialNetwork() {
    	return socialNetwork;
    }
    
    public ResourceSet getResourceSet() {
		return resourceSet;
	}
    
    public void setSocialNetwork(SocialNetworkRoot network, ResourceSet resourceSet) {
    	socialNetwork = network;
    	this.resourceSet = resourceSet;
    }

    public abstract String Initial();

    public abstract String Update(ModelChangeSet changes);

	// some PostgreSQL-specific parameters like database name, connection string
	private final static String PG_DB_NAME = "ttc2018eval";
	private final static String PG_PORT = (System.getenv("PG_PORT")!=null)?System.getenv("PG_PORT"):"5432";
	private final static String PG_USER = "ttcuser";
	private final static String PG_PASS = "secret";
	private final static String PG_URL = String.format("jdbc:postgresql://localhost:%1$s/%2$s", PG_PORT, PG_DB_NAME);
	private final static String PG_LOAD_SCRIPT = "load-scripts/load.sh";

	private String DataPath;

	Solution(String DataPath) throws IOException, InterruptedException {
		this.DataPath = new File(DataPath).getCanonicalPath();

		loadSchema();
	}

	void loadSchema() throws IOException, InterruptedException {
		runLoadSh("schema-only");
	}
	void loadData() throws IOException, InterruptedException {
		runLoadSh("data-only");
	}
	void runLoadSh(String option) throws IOException, InterruptedException
	{
		ProcessBuilder pb = new ProcessBuilder(PG_LOAD_SCRIPT, option);
		Map<String, String> env = pb.environment();
		env.put("PG_DATA_DIR", DataPath);
		env.put("PG_DB_NAME", PG_DB_NAME);
		env.put("PG_USER", PG_USER);
		env.put("PG_PORT", PG_PORT);

		File log = new File("log.txt");
		pb.redirectErrorStream(true);
		pb.redirectOutput(ProcessBuilder.Redirect.appendTo(log));
		Process p = pb.start();
		p.waitFor();
	}

	static String formatEnvVar(String name, String value) {
		return String.format("%1$s=%2$s", name, value);
	}
}
