package ttc2018.sqlmodel;

import java.util.ArrayList;
import java.util.Date;

public class Comments extends Submissions {
    ArrayList<Long> previousids = new ArrayList<>();
    ArrayList<Long> postids = new ArrayList<>();

    public void addComment(String id, Date ts, String content, String submitterid, String previousid, String postid) {
        addComment(toNumber(id), ts, content, toNumber(submitterid), toNumber(previousid), toNumber(postid));
    }

    public void addComment(long id, Date ts, String content, long submitterid, long previousid, long postid) {
        super.addSubmission(id, ts, content, submitterid);

        previousids.add(previousid);
        postids.add(postid);

        printCSV(id, ts, content, submitterid, previousid, postid);
    }
}
