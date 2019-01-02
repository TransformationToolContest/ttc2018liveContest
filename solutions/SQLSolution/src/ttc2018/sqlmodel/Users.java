package ttc2018.sqlmodel;

import java.util.ArrayList;

public class Users extends SqlCollectionBase {
    ArrayList<Long> ids = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();

    public void addUser(String id, String name) {
        addUser(toNumber(id), name);
    }

    public void addUser(long id, String name) {
        ids.add(id);
        names.add(name);

        printCSV(id, name);
    }
}
