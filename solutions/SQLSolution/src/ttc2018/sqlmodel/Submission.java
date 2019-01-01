package ttc2018.sqlmodel;

import java.util.Date;

abstract class Submission extends SqlModelBase {
    int id;
    Date ts;
    String content;
    int submitterid;

    Submission(int id, Date ts, String content, int submitterid) {
        super();
        this.id=id;
        this.ts=ts;
        this.content=content;
        this.submitterid=submitterid;
    }
}
