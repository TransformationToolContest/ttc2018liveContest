package ttc2018;

import org.neo4j.graphdb.GraphDatabaseService;

import java.io.File;
import java.io.IOException;

public class SolutionQ1 extends Solution {

	public SolutionQ1(String DataPath) throws IOException, InterruptedException {
		super(DataPath);

		Query.Q1_INITIAL.setSolution(this);
		Query.Q1_UPDATE.setSolution(this);
		Query.Q1_RETRIEVE.setSolution(this);
	}
	
	@Override
	public String Initial() {
		runVoidQuery(Query.Q1_INITIAL);
		String result = runReadQuery(Query.Q1_RETRIEVE);

		return result;
	}

	@Override
	public String Update(File changes) {
		beforeUpdate(changes);

		runVoidQuery(Query.Q1_UPDATE);
		String result = runReadQuery(Query.Q1_RETRIEVE);

		afterUpdate();

		return result;
	}
}
