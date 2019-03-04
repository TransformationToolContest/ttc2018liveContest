package ttc2018;

import Changes.ModelChangeSet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

public class SolutionQ1Batch extends Solution {

	public SolutionQ1Batch(String DataPath) throws IOException, InterruptedException {
		super(DataPath);

		Connection conn = getDbConnection();
		Query.Q1_BATCH.prepareStatement(conn);
	}
	
	@Override
	public String Initial() {
		String result = runReadQuery(Query.Q1_BATCH);

		return result;
	}

	@Override
	public String Update(ModelChangeSet changes) {
		beforeUpdate(changes);

		String result = runReadQuery(Query.Q1_BATCH);

		afterUpdate();

		return result;
	}

	@Override
	public String Update(File changes) {
		beforeUpdate(changes);

		String result = runReadQuery(Query.Q1_BATCH);

		afterUpdate();

		return result;
	}
}
