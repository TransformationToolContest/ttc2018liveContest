package ttc2018.sqlmodel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public abstract class SqlCollectionBase {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 'Z' should be added (with quotes) is needed
    // input is parsed as local date, we do so with on the output side
    //TimeZone tz = TimeZone.getTimeZone("UTC");
    //df.setTimeZone(tz);

    public void printCSV(Object... objects) {
        ArrayList<String> strings = new ArrayList<>();
        for (Object o: objects) {
            if (o instanceof Date) {
                strings.add(df.format(o));
            } else if (o == null) {
                strings.add(null);
            } else {
                strings.add(o.toString());
            }
        }
        System.out.print(this.getClass().getSimpleName()+": ");
        System.out.println(String.join("|", strings));
    }

    public void printCSV(String... strings) {
        System.out.print(this.getClass().getSimpleName()+": ");
        System.out.println(String.join("|", strings));
    }

    Long toNumber(String str) {
        if (str == null) {
            return -2L; // workaround missing model references
        } else {
            return Long.valueOf(str);
        }
    }
}
