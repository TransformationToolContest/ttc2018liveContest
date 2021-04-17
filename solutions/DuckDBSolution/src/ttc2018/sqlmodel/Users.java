package ttc2018.sqlmodel;

public class Users extends SqlCollectionBase<Long, User> {
    public void addUser(String id, String name) {
        addUser(toNumber(id), name);
    }

    public void addUser(long id, String name) {
        Long k = Long.valueOf(id);
        if (!elements.containsKey(k)) {
            elements.put(k, new User(id, name));

            printCSV(id, name);
        }
    }

    @Override
    public SqlTable getSqlTable() {
        return SqlTable.USERS;
    }
}
