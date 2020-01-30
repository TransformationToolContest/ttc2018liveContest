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
    Q1_INIT(Paths.get("q1-init.sql")),
    Q1_MAINTAIN(Paths.get("q1-maintain.sql")),
    Q1_RETRIEVE(Paths.get("q1-retrieve.sql")),
    Q2_BATCH(Paths.get("q2.sql")),
    Q2_CF_TRUNCATE("truncate table comment_friends"),
    Q2_CF_INIT(Paths.get("q2-cf-init.sql")),
    Q2_CF_MAINTAIN(Paths.get("q2-cf-maintain.sql")),
    Q2_CFC_INIT_STEP1(Paths.get("q2-cfc-init-step1.sql")),
    Q2_CFC_INIT_STEP2(Paths.get("q2-cfc-init-step2.sql")),
    Q2_CFC_MAINTAIN(Paths.get("q2-cfc-maintain.sql")),
    Q2_RETRIEVE(Paths.get("q2-retrieve.sql")),
    Q2_INFO_COUNT_LIKES_D("select count(*) as c1, count(*) as c2 from likes_d"),
    Q2_INFO_COUNT_COMMENT_FRIENDS_D("select count(*) as c1, count(*) as c2 from comment_friends_d"),
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

