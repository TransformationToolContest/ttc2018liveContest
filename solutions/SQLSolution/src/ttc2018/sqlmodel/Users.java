package ttc2018.sqlmodel;

import java.util.ArrayList;

public class Users extends SqlCollectionBase<User> {
    public void addUser(String id, String name) {
        addUser(toNumber(id), name);
    }

    public void addUser(long id, String name) {
        elements.add(new User(id, name));

        printCSV(id, name);
    }

    @Override
    public SqlTable getSqlTable() {
        return SqlTable.USERS;
    }
}
