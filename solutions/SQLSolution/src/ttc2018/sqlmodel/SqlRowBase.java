package ttc2018.sqlmodel;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class SqlRowBase {
    public abstract void setForInsert(PreparedStatement insert) throws SQLException;
}
