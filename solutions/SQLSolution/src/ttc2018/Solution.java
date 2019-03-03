package ttc2018;

import Changes.ModelChangeSet;
import SocialNetwork.SocialNetworkRoot;
import org.eclipse.emf.ecore.resource.ResourceSet;
import ttc2018.sqlmodel.SqlCollectionBase;
import ttc2018.sqlmodel.SqlRowBase;
import ttc2018.sqlmodel.SqlTable;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Solution {
	protected SocialNetworkRoot socialNetwork;
	protected ResourceSet resourceSet;
	protected ModelChangeProcessor modelChangeProcessor;

	// see: getDbConnection()
	protected Connection dbConnection;

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

	/**
	 * Update reading changes from CSV file
	 */
	public abstract String Update(File changes);

	// some PostgreSQL-specific parameters like database name, connection string
	private final static String PG_DB_NAME = (System.getenv("PG_DB_NAME")!=null)?System.getenv("PG_DB_NAME"):"ttc2018eval";
	private final static String PG_PORT = (System.getenv("PG_PORT")!=null)?System.getenv("PG_PORT"):"5432";
	private final static String PG_USER = "ttcuser";
	private final static String PG_PASS = "secret";
	private final static String PG_URL = String.format("jdbc:postgresql://localhost:%1$s/%2$s", PG_PORT, PG_DB_NAME);
	private final static String PG_LOAD_SCRIPT = "load-scripts/load.sh";

	private String DataPath;

	Solution(String DataPath) throws IOException, InterruptedException {
		this.DataPath = new File(DataPath).getCanonicalPath();

		modelChangeProcessor = new ModelChangeProcessor();

		loadSchema();

		Connection conn = getDbConnection();
		for(SqlTable t: SqlTable.values()) {
			t.prepareStatements(conn);
		}
	}

	public Connection getDbConnection() {
		try {
			if (dbConnection == null || dbConnection.isClosed()) {
				dbConnection = DriverManager.getConnection(PG_URL, PG_USER, PG_PASS);
			}

			return dbConnection;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	String runReadQuery(Query q) {
		List<String> result = new ArrayList<>();

		try (ResultSet rs = q.getPreparedStatement().executeQuery()) {
			while (rs.next()) {
				if (LiveContestDriver.ShowScoresForValidation) {
					result.add(String.format("%1$s,%2$s", rs.getString(1), rs.getString(2)));
				} else {
					result.add(rs.getString(1));
				}
			}

			return String.join("|", result);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	void runVoidQuery(Query q) {
		try {
			q.getPreparedStatement().executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
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

	void beforeUpdate(ModelChangeSet changes) {
		modelChangeProcessor.resetCollections();
		modelChangeProcessor.processChangeSet(changes);
		beforeUpdateCommon();
	}

	void beforeUpdate(File changes) {
		modelChangeProcessor.resetCollections();
		modelChangeProcessor.processChangeSet(changes);
		beforeUpdateCommon();
	}

	void beforeUpdateCommon() {
		for(SqlCollectionBase<SqlRowBase> c: modelChangeProcessor.getCollections()) {
			PreparedStatement insert = c.getSqlTable().getInsertPreparedStatement();
			int cnt = 0;
			try {
				for (SqlRowBase r : c) {
					r.setForInsert(insert);
					insert.addBatch();
					if (++cnt == 50) {
						insert.executeBatch();
					}
				}
				if (cnt > 0) {
					insert.executeBatch();
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}
	void afterUpdate() {
		for(SqlTable t: SqlTable.values()) {
			t.executeD2I();
		}
	}
}
