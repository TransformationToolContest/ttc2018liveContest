package ttc2018.sqlmodel;

import java.util.Date;

public class Comments extends SqlCollectionBase<Long, Comment> {
    public void addComment(String id, String ts, String content, String submitterid, String parentid) {
        addComment(id, toDate(ts), content, submitterid, parentid);
    }

    public void addComment(String id, Date ts, String content, String submitterid, String parentid) {
        addComment(toNumber(id), ts, content, toNumber(submitterid), toNumber(parentid));
    }

    public void addComment(long id, Date ts, String content, long submitterid, long parentid) {
        Long k = Long.valueOf(id);
        if (!elements.containsKey(k)) {
            elements.put(k, new Comment(id, ts, content, submitterid, parentid));

            printCSV(id, ts, content, submitterid, parentid);
        }
    }

    @Override
    public SqlTable getSqlTable() {
        return SqlTable.COMMENTS;
    }
}
