package ttc2018.sqlmodel;

public class User extends SqlModelBase {
    int id;
    String name;

    public User(int id, String name) {
        super();
        this.id=id;
        this.name=name;
    }
}
