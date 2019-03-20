package ttc2018;

import org.neo4j.graphdb.GraphDatabaseService;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

public class SolutionQ2 extends Solution {

	public SolutionQ2(String DataPath) throws IOException, InterruptedException {
		super(DataPath);

		GraphDatabaseService conn = getDbConnection();
		Query.Q2_CF_INITIAL.setGraphDb(conn);
		Query.Q2_CF_UPDATE.setGraphDb(conn);
		Query.Q2_CFC_PREPARE.setGraphDb(conn);
		Query.Q2_CFC_UPDATE_INIT.setGraphDb(conn);
		Query.Q2_CFC_UPDATE_MAINTAIN.setGraphDb(conn);
		Query.Q2_RETRIEVE.setGraphDb(conn);
	}

	@Override
	public String Initial() {
		runVoidQuery(Query.Q2_CF_INITIAL);
		runVoidQuery(Query.Q2_CFC_PREPARE);
		runVoidQuery(Query.Q2_CFC_UPDATE_INIT);
		String result = runReadQuery(Query.Q2_RETRIEVE);

		return result;
	}

	@Override
	public String Update(File changes) {
		beforeUpdate(changes);

		runVoidQuery(Query.Q2_CF_UPDATE);
		runVoidQuery(Query.Q2_CFC_UPDATE_MAINTAIN);
		String result = runReadQuery(Query.Q2_RETRIEVE);

		afterUpdate();

		return result;
	}
}
