package ttc2018.sqlmodel;

public class Friend extends SqlModelBase {
    int user1id;
    int user2id;

    public Friend(int user1id, int user2id) {
        super();
        this.user1id=user1id;
        this.user2id=user2id;
    }
}
