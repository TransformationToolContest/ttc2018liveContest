package ttc2018.sqlmodel;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class User extends SqlRowBase {
    long id;
    String name;

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public void setForInsert(PreparedStatement insert) throws SQLException {
        insert.setLong(1, id);
        insert.setString(2, name);
    }
}
