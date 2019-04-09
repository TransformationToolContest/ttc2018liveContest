package ttc2018;

import org.neo4j.graphdb.Result;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public enum Query {
    Q1_BATCH(Paths.get("q1.cypher")),
    Q1_INITIAL(Paths.get("q1-initial.cypher")),
    Q1_RETRIEVE(Paths.get("q1-retrieve.cypher")),

    Q2_BATCH(Paths.get("q2.cypher")),
    Q2_INITIAL_OVERLAY_GRAPH(Paths.get("q2-initial-overlay-graph.cypher")),
    Q2_DELETE_OVERLAY_GRAPH("MATCH ()-[friendLikes:FRIEND_WHO_LIKES_COMMENT]->() DELETE friendLikes"),
    Q2_INITIAL_SCORE(Paths.get("q2-initial-score.cypher")),
    Q2_RETRIEVE(Paths.get("q2-retrieve.cypher")),
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

    protected Solution solution;

    public void setSolution(Solution solution) {
        this.solution = solution;
    }

    public Result execute() {
        return solution.getDbConnection().execute(queryText);
    }
}

