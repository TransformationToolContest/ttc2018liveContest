package ttc2018.sqlmodel;

public class Likes extends SqlCollectionBase<Like> {
    public void addLike(String userid, String commentd) {
        addLike(toNumber(userid), toNumber(commentd));
    }

    public void addLike(long userid, long commentid) {
        elements.add(new Like(userid, commentid));

        printCSV(userid, commentid);
    }

    @Override
    public SqlTable getSqlTable() {
        return SqlTable.LIKES;
    }
}
