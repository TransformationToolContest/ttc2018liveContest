package ttc2018.sqlmodel;

import java.util.AbstractMap;

public class Likes extends SqlCollectionBase<AbstractMap.SimpleImmutableEntry<Long, Long>, Like> {
    public void addLike(String userid, String commentd) {
        addLike(toNumber(userid), toNumber(commentd));
    }

    public void addLike(long userid, long commentid) {
        AbstractMap.SimpleImmutableEntry<Long, Long> k = new AbstractMap.SimpleImmutableEntry<>(userid, commentid);
        if (!elements.containsKey(k)) {
            elements.put(k, new Like(userid, commentid));

            printCSV(userid, commentid);
        }
    }

    @Override
    public SqlTable getSqlTable() {
        return SqlTable.LIKES;
    }
}
