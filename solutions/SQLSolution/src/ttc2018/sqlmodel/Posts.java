package ttc2018.sqlmodel;

import java.util.Date;

public class Posts extends Submissions {
    public void addPost(String id, Date ts, String content, String submitterid) {
        addPost(Long.valueOf(id), ts, content, Long.valueOf(submitterid));
    }


    public void addPost(long id, Date ts, String content, long submitterid) {
        super.addSubmission(id, ts, content, submitterid);

        printCSV(id, ts, content, submitterid);
    }
}
