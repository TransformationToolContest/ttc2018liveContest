package ttc2018;

import org.neo4j.graphdb.Result;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public enum Query {
    Q1_BATCH(Paths.get("q1_batch.cypher")),
    Q1_INITIAL(Paths.get("q1_initial.cypher")),
    Q1_RETRIEVE(Paths.get("q1_retrieve.cypher")),

    Q2_BATCH(Paths.get("q2_batch.cypher")),
    Q2_BATCH_ALGO(Paths.get("q2_algo.cypher")),
    Q2_BATCH_ALGO_WITH_FILTERED_EDGES(Paths.get("q2_algo_with_filtered_edges.cypher")),
    Q2_INITIAL_OVERLAY_GRAPH(Paths.get("q2_initial_overlay_graph.cypher")),
    Q2_INITIAL_DYNAMIC_LIKES_LABELS(Paths.get("q2_initial_dynamic_likes_labels.cypher")),
    Q2_DELETE_OVERLAY_GRAPH(Paths.get("q2_delete_overlay_graph.cypher")),
    Q2_INITIAL_SCORE(Paths.get("q2_initial_score.cypher")),
    Q2_INITIAL_COMPONENTS_AND_SCORE(Paths.get("q2_initial_components_and_score.cypher")),
    Q2_INITIAL_COMPONENTS_PERIODIC(Paths.get("q2_initial_components_periodic.cypher")),
    Q2_INITIAL_SCORE_FROM_EXPLICIT_COMPONENTS(Paths.get("q2_initial_score_from_explicit_components.cypher")),
    Q2_INITIAL_ZERO_SCORE(Paths.get("q2_initial_zero_score.cypher")),
    Q2_UPDATE_OVERLAY_GRAPH_FRIEND_EDGE(Paths.get("q2_update_overlay_graph_friend_edge.cypher")),
    Q2_UPDATE_OVERLAY_GRAPH_LIKES_EDGE(Paths.get("q2_update_overlay_graph_likes_edge.cypher")),
    Q2_MERGE_COMPONENTS_AFTER_FRIEND_EDGE(Paths.get("q2_merge_components_after_friend_edge.cypher")),
    Q2_MERGE_COMPONENTS_AFTER_LIKES_EDGE(Paths.get("q2_merge_components_after_likes_edge.cypher")),
    Q2_RECALCULATE_SCORE(Paths.get("q2_recalculate_score.cypher")),
    Q2_RETRIEVE(Paths.get("q2_retrieve.cypher")),
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

    public Result execute(Solution solution, Map<String, Object> parameters) {
        return solution.getDbConnection().execute(queryText, parameters);
    }
}
