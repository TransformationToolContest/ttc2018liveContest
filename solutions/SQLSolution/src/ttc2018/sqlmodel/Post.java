package ttc2018.sqlmodel;

import java.util.Date;

public class Post extends Submission {
    public Post(int id, Date ts, String content, int submitterid) {
        super(id, ts, content, submitterid);
    }
}
