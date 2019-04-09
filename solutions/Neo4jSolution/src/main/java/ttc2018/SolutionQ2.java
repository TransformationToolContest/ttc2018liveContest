package ttc2018;

import org.neo4j.graphdb.GraphDatabaseService;

import java.io.File;
import java.io.IOException;

import static ttc2018.Labels.Comment;

public class SolutionQ2 extends Solution {

    public SolutionQ2(String DataPath) throws IOException, InterruptedException {
        super(DataPath);

        Query.Q2_INITIAL_OVERLAY_GRAPH.setSolution(this);
        Query.Q2_DELETE_OVERLAY_GRAPH.setSolution(this);
        Query.Q2_INITIAL_SCORE.setSolution(this);
        Query.Q2_RETRIEVE.setSolution(this);
    }

    @Override
    protected void addConstraintsAndIndicesInTx(GraphDatabaseService dbConnection) {
        super.addConstraintsAndIndicesInTx(dbConnection);

        dbConnection.schema()
                .indexFor(Comment)
                .on(SUBMISSION_SCORE_PROPERTY)
                .create();

        // note: cannot create index on commentId property of FRIEND_WHO_LIKES_COMMENT edge
    }

    @Override
    public String Initial() {
        runVoidQuery(Query.Q2_INITIAL_OVERLAY_GRAPH);
        runVoidQuery(Query.Q2_INITIAL_SCORE);
        String result = runReadQuery(Query.Q2_RETRIEVE);

        return result;
    }

    @Override
    public String Update(File changes) {
        beforeUpdate(changes);

        runVoidQuery(Query.Q2_DELETE_OVERLAY_GRAPH);
        runVoidQuery(Query.Q2_INITIAL_OVERLAY_GRAPH);
        runVoidQuery(Query.Q2_INITIAL_SCORE);
        String result = runReadQuery(Query.Q2_RETRIEVE);

        afterUpdate();

        return result;
    }
}
