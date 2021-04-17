package ttc2018.sqlmodel;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class Comment extends Submission {
    long parentid;

    public Comment(long id, Date ts, String content, long submitterid, long parentid) {
        super(id, ts, content, submitterid);

        this.parentid = parentid;
    }

    public void setForInsert(PreparedStatement insert) throws SQLException {
        super.setForInsert(insert);
        insert.setLong(5, parentid);
    }
}
