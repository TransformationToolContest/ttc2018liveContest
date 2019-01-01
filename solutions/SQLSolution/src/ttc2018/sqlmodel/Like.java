package ttc2018.sqlmodel;

public class Like extends SqlModelBase {
    int userid;
    int commentid;

    public Like(int userid, int commentid) {
        super();
        this.userid=userid;
        this.commentid=commentid;
    }
}
