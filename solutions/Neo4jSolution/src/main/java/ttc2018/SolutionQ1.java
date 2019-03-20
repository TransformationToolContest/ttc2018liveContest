package ttc2018;

import org.neo4j.graphdb.GraphDatabaseService;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

public class SolutionQ1 extends Solution {

	public SolutionQ1(String DataPath) throws IOException, InterruptedException {
		super(DataPath);

		GraphDatabaseService conn = getDbConnection();
		Query.Q1_INITIAL.setGraphDb(conn);
		Query.Q1_UPDATE.setGraphDb(conn);
		Query.Q1_RETRIEVE.setGraphDb(conn);
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
