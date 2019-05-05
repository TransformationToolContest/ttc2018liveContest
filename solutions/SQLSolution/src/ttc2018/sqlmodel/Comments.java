package ttc2018.sqlmodel;

import java.util.Date;

public class Comments extends SqlCollectionBase<Long, Comment> {
    public void addComment(String id, String ts, String content, String submitterid, String previousid, String postid) {
        addComment(id, toDate(ts), content, submitterid, previousid, postid);
    }

    public void addComment(String id, Date ts, String content, String submitterid, String previousid, String postid) {
        addComment(toNumber(id), ts, content, toNumber(submitterid), toNumber(previousid), toNumber(postid));
    }

    public void addComment(long id, Date ts, String content, long submitterid, long previousid, long postid) {
        Long k = Long.valueOf(id);
        if (!elements.containsKey(k)) {
            elements.put(k, new Comment(id, ts, content, submitterid, previousid, postid));

            printCSV(id, ts, content, submitterid, previousid, postid);
        }
    }

    @Override
    public SqlTable getSqlTable() {
        return SqlTable.COMMENTS;
    }
}
