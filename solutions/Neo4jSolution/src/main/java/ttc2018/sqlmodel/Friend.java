package ttc2018.sqlmodel;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Friend extends SqlRowBase {
    long user1id;
    long user2id;

    public Friend(long user1id, long user2id) {
        this.user1id = user1id;
        this.user2id = user2id;
    }

    @Override
    public void setForInsert(PreparedStatement insert) throws SQLException {
        insert.setLong(1, user1id);
        insert.setLong(2, user2id);
    }
}
