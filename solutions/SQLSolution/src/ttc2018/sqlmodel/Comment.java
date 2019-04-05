package ttc2018.sqlmodel;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class Comment extends Submission {
    long previousid;
    long postid;

    public Comment(long id, Date ts, String content, long submitterid, long previousid, long postid) {
        super(id, ts, content, submitterid);

        this.previousid = previousid;
        this.postid = postid;
    }

    public void setForInsert(PreparedStatement insert) throws SQLException {
        super.setForInsert(insert);
        insert.setLong(5, previousid);
        insert.setLong(6, postid);
    }
}
