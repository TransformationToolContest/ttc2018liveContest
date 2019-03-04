package ttc2018;

import Changes.ModelChangeSet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

public class SolutionQ2 extends Solution {

	public SolutionQ2(String DataPath) throws IOException, InterruptedException {
		super(DataPath);

		Connection conn = getDbConnection();
		Query.Q2_CF_INITIAL.prepareStatement(conn);
		Query.Q2_CF_UPDATE.prepareStatement(conn);
		Query.Q2_CFC_PREPARE.prepareStatement(conn);
		Query.Q2_CFC_UPDATE_INIT.prepareStatement(conn);
		Query.Q2_CFC_UPDATE_MAINTAIN.prepareStatement(conn);
		Query.Q2_RETRIEVE.prepareStatement(conn);
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
	public String Update(ModelChangeSet changes) {
		beforeUpdate(changes);

		runVoidQuery(Query.Q2_CF_UPDATE);
		runVoidQuery(Query.Q2_CFC_UPDATE_MAINTAIN);
		String result = runReadQuery(Query.Q2_RETRIEVE);

		afterUpdate();

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
