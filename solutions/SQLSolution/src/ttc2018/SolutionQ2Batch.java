package ttc2018;

import Changes.ModelChangeSet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

public class SolutionQ2Batch extends Solution {

	public SolutionQ2Batch(String DataPath) throws IOException, InterruptedException {
		super(DataPath);

		Connection conn = getDbConnection();
		Query.Q2_BATCH.prepareStatement(conn);
	}

	@Override
	public String Initial() {
		String result = runReadQuery(Query.Q2_BATCH);

		return result;
	}

	@Override
	public String Update(ModelChangeSet changes) {
		beforeUpdate(changes);

		String result = runReadQuery(Query.Q2_BATCH);

		afterUpdate();

		return result;
	}

	@Override
	public String Update(File changes) {
		beforeUpdate(changes);

		String result = runReadQuery(Query.Q2_BATCH);

		afterUpdate();

		return result;
	}
}
