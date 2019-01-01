package ttc2018.sqlmodel;

import java.util.ArrayList;
import java.util.Date;

abstract class Submissions extends SqlCollectionBase {
    ArrayList<Long> ids = new ArrayList<>();
    ArrayList<java.sql.Date> tss = new ArrayList<>();
    ArrayList<String> contents = new ArrayList<>();
    ArrayList<Long> submitterids = new ArrayList<>();


    void addSubmission(long id, Date ts, String content, long submitterid) {
        ids.add(id);
        tss.add(new java.sql.Date(ts.getTime()));
        contents.add(content);
        submitterids.add(submitterid);
    }
}
