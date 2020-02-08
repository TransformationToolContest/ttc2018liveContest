package ttc2018.sqlmodel;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class Comment extends Submission {
    long parentid;
    long postid;

    public Comment(long id, Date ts, String content, long submitterid, long parentid, long postid) {
        super(id, ts, content, submitterid);

        this.parentid = parentid;
        this.postid = postid;
    }

    public void setForInsert(PreparedStatement insert) throws SQLException {
        super.setForInsert(insert);
        insert.setLong(5, parentid);
        insert.setLong(6, postid);
    }
}
