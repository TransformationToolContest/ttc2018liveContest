package ttc2018.sqlmodel;

import java.util.ArrayList;

public class Friends extends SqlCollectionBase {
    ArrayList<Long> user1ids = new ArrayList<>();
    ArrayList<Long> user2ids = new ArrayList<>();

    public void addFriend(String user1id, String user2id) {
        addFriend(toNumber(user1id), toNumber(user2id));
    }

    public void addFriend(long user1id, long user2id) {
        user1ids.add(user1id);
        user2ids.add(user2id);

        printCSV(user1id, user2id);
    }
}
