package ttc2018;

import Changes.ModelChangeSet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

public class SolutionQ2 extends Solution {

	public SolutionQ2(String DataPath) throws IOException, InterruptedException {
		super(DataPath);

		Connection conn = getDbConnection();
		Query.Q2_INITIAL.prepareStatement(conn);
		Query.Q2_CF_TRUNCATE.prepareStatement(conn);
		Query.Q2_CF_POPULATE.prepareStatement(conn);
	}

	@Override
	public String Initial() {
		runVoidQuery(Query.Q2_CF_POPULATE);
		String result = runReadQuery(Query.Q2_INITIAL);

		return result;
	}

	@Override
	public String Update(ModelChangeSet changes) {
		beforeUpdate(changes);

		runVoidQuery(Query.Q2_CF_TRUNCATE);
		runVoidQuery(Query.Q2_CF_POPULATE);
		String result = runReadQuery(Query.Q2_INITIAL);

		afterUpdate();

		return result;
	}

	@Override
	public String Update(File changes) {
		beforeUpdate(changes);

		runVoidQuery(Query.Q2_CF_TRUNCATE);
		runVoidQuery(Query.Q2_CF_POPULATE);
		String result = runReadQuery(Query.Q2_INITIAL);

		afterUpdate();

		return result;
	}
}
