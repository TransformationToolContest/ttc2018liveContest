package ttc2018;

import org.neo4j.graphalgo.UnionFindProc;
import org.neo4j.internal.kernel.api.exceptions.KernelException;

import java.io.File;
import java.io.IOException;

public class SolutionQ2Batch extends Solution {

    private final Query q2Batch;
    private Tool tool;

    public SolutionQ2Batch(String DataPath, String toolName) throws IOException, InterruptedException {
        super(DataPath);

        tool = Tool.valueOf(toolName);

        switch (tool) {
            case Neo4jSolutionBatch:
                q2Batch = Query.Q2_BATCH;

                Query.Q2_DELETE_OVERLAY_GRAPH.setSolution(this);
                Query.Q2_INITIAL_OVERLAY_GRAPH.setSolution(this);
                break;
            case Neo4jSolutionBatch_algo:
                q2Batch = Query.Q2_BATCH_ALGO;
                break;
            default:
                throw new IllegalArgumentException();
        }
        q2Batch.setSolution(this);
    }

    public enum Tool {
        Neo4jSolutionBatch,
        Neo4jSolutionBatch_algo,
    }

    @Override
    protected void initializeDb() throws KernelException {
        super.initializeDb();

        if (tool == Tool.Neo4jSolutionBatch_algo)
            registerProcedure(graphDb, UnionFindProc.class);
    }

    @Override
    public String Initial() {

        if (tool == Tool.Neo4jSolutionBatch) {
            runVoidQuery(Query.Q2_INITIAL_OVERLAY_GRAPH);
        }
        String result = runReadQuery(q2Batch);

        return result;
    }

    @Override
    public String Update(File changes) {
        beforeUpdate(changes);

        if (tool == Tool.Neo4jSolutionBatch) {
            runVoidQuery(Query.Q2_DELETE_OVERLAY_GRAPH);
            runVoidQuery(Query.Q2_INITIAL_OVERLAY_GRAPH);
        }
        String result = runReadQuery(q2Batch);

        afterUpdate();

        return result;
    }
}
