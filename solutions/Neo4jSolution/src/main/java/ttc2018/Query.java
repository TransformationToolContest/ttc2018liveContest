package ttc2018;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Result;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public enum Query {
    Q1_BATCH(Paths.get("q1.cypher")),
//    Q1_INITIAL(Paths.get("q1-initial.cypher")),
//    Q1_UPDATE(Paths.get("q1-update.cypher")),
//    Q1_RETRIEVE(Paths.get("q1-retrieve.cypher")),
    Q1_INITIAL(""),
    Q1_UPDATE(""),
    Q1_RETRIEVE(""),

    Q2_BATCH(Paths.get("q2.cypher")),
//    Q2_CF_INITIAL(Paths.get("q2-cf-initial.cypher")),
//    Q2_CF_UPDATE(Paths.get("q2-cf-update.cypher")),
//    Q2_CFC_PREPARE(Paths.get("q2-cfc-prepare.cypher")),
//    Q2_CFC_UPDATE_INIT(Paths.get("q2-cfc-update-during-initial.cypher")),
//    Q2_CFC_UPDATE_MAINTAIN(Paths.get("q2-cfc-update-during-update.cypher")),
//    Q2_RETRIEVE(Paths.get("q2-retrieve.cypher")),
    Q2_CF_INITIAL(""),
    Q2_CF_UPDATE(""),
    Q2_CFC_PREPARE(""),
    Q2_CFC_UPDATE_INIT(""),
    Q2_CFC_UPDATE_MAINTAIN(""),
    Q2_RETRIEVE(""),
    ;

    public static final String ID_COLUMN_NAME = "id";
    public static final String SCORE_COLUMN_NAME = "score";

    public final String queryText;

    Query(Path f) {
        try {
            queryText = new String(Files.readAllBytes(f));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    Query(String s) {
        queryText = s;
    }

    protected GraphDatabaseService graphDb;

    public void setGraphDb(GraphDatabaseService graphDb) {
        this.graphDb = graphDb;
    }

    public Result execute() {
        return graphDb.execute(queryText);
    }
}

