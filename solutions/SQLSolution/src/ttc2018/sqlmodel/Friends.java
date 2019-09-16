package ttc2018.sqlmodel;

import java.util.AbstractMap;

public class Friends extends SqlCollectionBase<AbstractMap.SimpleImmutableEntry<Long, Long>, Friend> {
    public void addFriend(String user1id, String user2id) {
        addFriend(toNumber(user1id), toNumber(user2id));
    }

    public void addFriend(long user1id, long user2id) {
        AbstractMap.SimpleImmutableEntry<Long, Long> k = new AbstractMap.SimpleImmutableEntry<>(user1id, user2id);
        if (!elements.containsKey(k)) {
            elements.put(k, new Friend(user1id, user2id));

            printCSV(user1id, user2id);
        }
    }

    @Override
    public SqlTable getSqlTable() {
        return SqlTable.FRIENDS;
    }
}
