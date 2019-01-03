package ttc2018;

import Changes.ModelChangeSet;

import java.io.IOException;
import java.sql.Connection;

public class SolutionQ1 extends Solution {

	public SolutionQ1(String DataPath) throws IOException, InterruptedException {
		super(DataPath);

		Connection conn = getDbConnection();
		Query.Q1_INITIAL.prepareStatement(conn);
	}
	
	@Override
	public String Initial() {
		String result = runReadQuery(Query.Q1_INITIAL);

		return result;
	}

	@Override
	public String Update(ModelChangeSet changes) {
		beforeUpdate(changes);

		String result = runReadQuery(Query.Q1_INITIAL);

		afterUpdate();

		return result;
	}
}
