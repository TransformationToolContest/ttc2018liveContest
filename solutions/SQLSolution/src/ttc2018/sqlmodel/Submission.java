package ttc2018.sqlmodel;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

abstract class Submission extends SqlRowBase {
    long id;
    java.sql.Timestamp ts;
    String content;
    long submitterid;


    public Submission(long id, Date ts, String content, long submitterid) {
        this.id = id;
        this.ts = new java.sql.Timestamp(ts.getTime());
        this.content = content;
        this.submitterid = submitterid;
    }

    @Override
    public void setForInsert(PreparedStatement insert) throws SQLException {
        insert.setLong(1, id);
        insert.setTimestamp(2, ts);
        insert.setString(3, content);
        insert.setLong(4, submitterid);
        // should fields be added or removed here, adjust this method in subclasses
    }
}
