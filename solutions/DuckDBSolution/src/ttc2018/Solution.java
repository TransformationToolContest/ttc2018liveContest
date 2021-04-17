package ttc2018;

import Changes.ModelChangeSet;
import SocialNetwork.SocialNetworkRoot;
import ttc2018.sqlmodel.SqlCollectionBase;
import ttc2018.sqlmodel.SqlRowBase;
import ttc2018.sqlmodel.SqlTable;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Solution {
	protected SocialNetworkRoot socialNetwork;
	protected ModelChangeProcessor modelChangeProcessor;

	// see: getDbConnection()
	protected Connection dbConnection;

    public SocialNetworkRoot getSocialNetwork() {
    	return socialNetwork;
    }
    
    public void setSocialNetwork(SocialNetworkRoot network) {
    	socialNetwork = network;
    }

    public abstract String Initial();

    public abstract String Update(ModelChangeSet changes);

	/**
	 * Update reading changes from CSV file
	 */
	public abstract String Update(File changes);

	private String DataPath;

	Solution(String DataPath) throws IOException, InterruptedException {
		this.DataPath = new File(DataPath).getCanonicalPath();

		modelChangeProcessor = new ModelChangeProcessor();
	}

	void prepareStatements() {
		Connection conn = getDbConnection();
		for(SqlTable t: SqlTable.values()) {
			t.prepareStatements(conn);
		}
	}

	public Connection getDbConnection() {
		try {
			Connection dbConnection = DriverManager.getConnection("jdbc:duckdb:");
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

	/**
	 * Runs a read query and return it's single integer result from  the resultset's first row and first column.
	 */
	int runSingleIntReadQuery(Query q) {
		try (ResultSet rs = q.getPreparedStatement().executeQuery()) {
			rs.next();
			return rs.getInt(1);
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
		for(SqlCollectionBase<Object, SqlRowBase> c: modelChangeProcessor.getCollections()) {
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
