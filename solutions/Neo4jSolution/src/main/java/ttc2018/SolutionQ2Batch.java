package ttc2018;

import org.neo4j.exceptions.KernelException;
import org.neo4j.graphalgo.wcc.WccStreamProc;

import java.io.File;
import java.io.IOException;

public class SolutionQ2Batch extends Solution {

    private final Query q2Batch;
    private Tool tool;

    public SolutionQ2Batch(String DataPath, String toolName) throws IOException, InterruptedException {
        super(DataPath);

        tool = Tool.valueOf(toolName);

        switch (tool) {
            case Neo4jSolutionBatch_rebuild_overlay:
                q2Batch = Query.Q2_BATCH_OVERLAY;
                break;
            case Neo4jSolutionBatch:
                q2Batch = Query.Q2_BATCH_ALGO;
                break;
            case Neo4jSolutionBatch_algo_with_filtered_edges:
                q2Batch = Query.Q2_BATCH_ALGO_WITH_FILTERED_EDGES;
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    public enum Tool {
        Neo4jSolutionBatch, // previously Neo4jSolutionBatch_algo
        Neo4jSolutionBatch_algo_with_filtered_edges,
        Neo4jSolutionBatch_rebuild_overlay,
    }

    @Override
    protected void initializeDb() throws KernelException {
        super.initializeDb();

        if (tool == Tool.Neo4jSolutionBatch || tool == Tool.Neo4jSolutionBatch_algo_with_filtered_edges)
            registerProcedure(graphDb, WccStreamProc.class);
    }

    @Override
    public String Initial() {

        if (tool == Tool.Neo4jSolutionBatch_rebuild_overlay) {
            runAndCommitVoidQuery(Query.Q2_INITIAL_OVERLAY_GRAPH);
        }
        String result = runReadQuery(q2Batch);

        return result;
    }

    @Override
    public String Update(File changes) {
        beforeUpdate(changes);

        if (tool == Tool.Neo4jSolutionBatch_rebuild_overlay) {
            runAndCommitVoidQuery(Query.Q2_DELETE_OVERLAY_GRAPH);
            runAndCommitVoidQuery(Query.Q2_INITIAL_OVERLAY_GRAPH);
        }

        return runReadQuery(q2Batch);
    }
}
