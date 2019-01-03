package ttc2018.sqlmodel;

import java.util.Date;

public class Post extends Submission {
    public Post(long id, Date ts, String content, long submitterid) {
        super(id, ts, content, submitterid);
    }
}
