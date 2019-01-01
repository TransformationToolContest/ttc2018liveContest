package ttc2018.sqlmodel;

import java.util.Date;

public class Comment extends Submission {
    int previousid;
    int postid;

    public Comment(int id, Date ts, String content, int submitterid, int previousid, int postid) {
        super(id, ts, content, submitterid);

        this.previousid=previousid;
        this.postid=postid;
    }
}
