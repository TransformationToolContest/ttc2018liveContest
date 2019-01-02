package ttc2018.sqlmodel;

import java.util.ArrayList;

public class Likes extends SqlCollectionBase {
    ArrayList<Long> userids = new ArrayList<>();
    ArrayList<Long> commentids = new ArrayList<>();

    public void addLike(String userid, String commentd) {
        addLike(toNumber(userid), toNumber(commentd));
    }

    public void addLike(long userid, long commentid) {
        userids.add(userid);
        commentids.add(commentid);

        printCSV(userid, commentid);
    }
}
