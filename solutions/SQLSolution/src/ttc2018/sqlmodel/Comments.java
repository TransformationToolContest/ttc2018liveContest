package ttc2018.sqlmodel;

import java.util.Date;

public class Comments extends SqlCollectionBase<Long, Comment> {
    public void addComment(String id, String ts, String content, String submitterid, String parentid, String postid) {
        addComment(id, toDate(ts), content, submitterid, parentid, postid);
    }

    public void addComment(String id, Date ts, String content, String submitterid, String parentid, String postid) {
        addComment(toNumber(id), ts, content, toNumber(submitterid), toNumber(parentid), toNumber(postid));
    }

    public void addComment(long id, Date ts, String content, long submitterid, long parentid, long postid) {
        Long k = Long.valueOf(id);
        if (!elements.containsKey(k)) {
            elements.put(k, new Comment(id, ts, content, submitterid, parentid, postid));

            printCSV(id, ts, content, submitterid, parentid, postid);
        }
    }

    @Override
    public SqlTable getSqlTable() {
        return SqlTable.COMMENTS;
    }
}
