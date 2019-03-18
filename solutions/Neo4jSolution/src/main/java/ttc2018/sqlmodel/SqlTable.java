package ttc2018.sqlmodel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public enum SqlTable {
    POSTS("posts"
            , new String[] {"id", "ts", "content", "submitterid"}),
    COMMENTS("comments"
            , new String[] {"id", "ts", "content", "submitterid", "previousid", "postid"}),
    USERS("users"
            , new String[] {"id", "name"}),
    FRIENDS("friends"
            , new String[] {"user1id", "user2id"}),
    LIKES("likes"
            , new String[] {"userid", "commentid"}),
    COMMENT_FRIENDS("comment_friends"
            , new String[] {"commentid", "user1id", "user2id"}),
    ;

    public final String tableName;
    public final String[] columns;
    SqlTable(String tableName, String[] columns) {
        this.tableName = tableName;
        this.columns = columns;
    }


    static final String SQL_DIFF_TO_IMAGE = "update %1$s set status='I' where status='D' ";
    String getD2Istatement() {
        return String.format(SQL_DIFF_TO_IMAGE, tableName);
    }
    String getInsertStatement() {
        String columnList = String.join(",", columns);
        ArrayList<String> placeholders = new ArrayList<>();
        for(String c: columns) {
            placeholders.add("?");
        }
        String valuesList = String.join(",", placeholders);

        return String.format("insert into %1$s_d (%2$s) values (%3$s)", tableName, columnList, valuesList);
    }

    protected PreparedStatement D2IPreparedStatement;
    protected PreparedStatement InsertPreparedStatement;
    public void prepareStatements(Connection conn) {
        try {
            D2IPreparedStatement = conn.prepareStatement(getD2Istatement());
            InsertPreparedStatement = conn.prepareStatement(getInsertStatement());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    PreparedStatement getD2IPreparedStatement() {
        if (D2IPreparedStatement == null) {
            throw new NullPointerException("Please invoke prepareStatements(Connection) first.");
        }
        return D2IPreparedStatement;
    }
    public PreparedStatement getInsertPreparedStatement() {
        if (InsertPreparedStatement == null) {
            throw new NullPointerException("Please invoke prepareStatements(Connection) first.");
        }
        return InsertPreparedStatement;
    }
    public void executeD2I() {
        try {
            getD2IPreparedStatement().executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
