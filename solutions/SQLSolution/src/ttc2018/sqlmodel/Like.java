package ttc2018.sqlmodel;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Like extends SqlRowBase {
    long userid;
    long commentid;

    public Like(long userid, long commentid) {
        this.userid = userid;
        this.commentid = commentid;
    }

    @Override
    public void setForInsert(PreparedStatement insert) throws SQLException {
        insert.setLong(1, userid);
        insert.setLong(2, commentid);
    }
}
