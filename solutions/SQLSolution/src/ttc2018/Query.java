package ttc2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public enum Query {
    Q1_INITIAL(Paths.get("q1.sql")),
    Q2_INITIAL(Paths.get("q2.sql")),
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

