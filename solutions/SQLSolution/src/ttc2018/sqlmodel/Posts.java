package ttc2018.sqlmodel;

import java.util.Date;

public class Posts extends SqlCollectionBase<Long, Post> {
    public void addPost(String id, String ts, String content, String submitterid) {
        addPost(id, toDate(ts), content, submitterid);
    }

    public void addPost(String id, Date ts, String content, String submitterid) {
        addPost(toNumber(id), ts, content, toNumber(submitterid));
    }

    public void addPost(long id, Date ts, String content, long submitterid) {
        Long k = Long.valueOf(id);
        if (!elements.containsKey(k)) {
            elements.put(k, new Post(id, ts, content, submitterid));

            printCSV(id, ts, content, submitterid);
        }
    }

    @Override
    public SqlTable getSqlTable() {
        return SqlTable.POSTS;
    }
}
