package ttc2018;

import Changes.ModelChangeSet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

public class SolutionQ1 extends Solution {

	public SolutionQ1(String DataPath) throws IOException, InterruptedException {
		super(DataPath);

		Connection conn = getDbConnection();
		Query.Q1_INITIAL.prepareStatement(conn);
		Query.Q1_UPDATE.prepareStatement(conn);
		Query.Q1_RETRIEVE.prepareStatement(conn);
	}
	
	@Override
	public String Initial() {
		runVoidQuery(Query.Q1_INITIAL);
		String result = runReadQuery(Query.Q1_RETRIEVE);

		return result;
	}

	@Override
	public String Update(ModelChangeSet changes) {
		beforeUpdate(changes);

		runVoidQuery(Query.Q1_UPDATE);
		String result = runReadQuery(Query.Q1_RETRIEVE);

		afterUpdate();

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
