package ttc2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public enum Query {
    Q1_BATCH(Paths.get("q1.sql")),
    Q1_INITIAL(Paths.get("q1-initial.sql")),
    Q1_UPDATE(Paths.get("q1-update.sql")),
    Q1_RETRIEVE(Paths.get("q1-retrieve.sql")),
    Q2_BATCH(Paths.get("q2.sql")),
    Q2_CF_TRUNCATE("truncate table comment_friends"),
    Q2_CF_INITIAL(Paths.get("q2-cf-initial.sql")),
    Q2_CF_UPDATE(Paths.get("q2-cf-update.sql")),
    Q2_CFC_PREPARE(Paths.get("q2-cfc-prepare.sql")),
    Q2_CFC_UPDATE_INIT(Paths.get("q2-cfc-update-during-initial.sql")),
    Q2_CFC_UPDATE_MAINTAIN(Paths.get("q2-cfc-update-during-update.sql")),
    Q2_RETRIEVE(Paths.get("q2-retrieve.sql")),
    ;


    public final String queryText;
    Query(Path f) {
        try {
            queryText = new String(Files.readAllBytes(f));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    Query(String s) {
        queryText = s;
    }

    protected PreparedStatement preparedStatement;
    public void prepareStatement(Connection conn) {
        try {
            preparedStatement = conn.prepareStatement(queryText);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public PreparedStatement getPreparedStatement() {
        if (preparedStatement == null) {
            throw new NullPointerException("Please invoke prepareStatements(Connection) first.");
        }
        return preparedStatement;
    }
}

