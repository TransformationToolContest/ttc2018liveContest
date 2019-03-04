package ttc2018.sqlmodel;

public class Friends extends SqlCollectionBase<Friend> {
    public void addFriend(String user1id, String user2id) {
        addFriend(toNumber(user1id), toNumber(user2id));
    }

    public void addFriend(long user1id, long user2id) {
        elements.add(new Friend(user1id, user2id));

        printCSV(user1id, user2id);
    }

    @Override
    public SqlTable getSqlTable() {
        return SqlTable.FRIENDS;
    }
}
