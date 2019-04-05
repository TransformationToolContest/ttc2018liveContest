package ttc2018.sqlmodel;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class SqlCollectionBase<T extends SqlRowBase> implements Iterable<T> {
    ArrayList<T> elements = new ArrayList<>();

    public Iterator<T> iterator() {
        return elements.iterator();
    }

    public abstract SqlTable getSqlTable();

    public static final boolean DO_PRINT = false;
    public static final String SEPARATOR = "|";

    PrintStream out = System.out;
    public void setOut(PrintStream ps) {
        out = ps;
    }

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
        printCSVAux(strings);
    }

    public void printCSV(String... strings) {
        printCSVAux(Arrays.asList(strings));
    }

    void printCSVAux(Iterable<String> strings) {
        if (DO_PRINT) {
            StringJoiner sj = new StringJoiner(SEPARATOR);
            sj.add(this.getClass().getSimpleName());
            for(String s: strings) {
                sj.add(s);
            }
            out.println(sj);
        }
    }

    Long toNumber(String str) {
        if (str == null) {
            return -2L; // workaround missing model references
        } else {
            return Long.valueOf(str);
        }
    }

    Date toDate(String str) {
        try {
            return df.parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
