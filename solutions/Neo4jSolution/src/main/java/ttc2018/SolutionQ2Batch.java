package ttc2018;

import java.io.File;
import java.io.IOException;

public class SolutionQ2Batch extends Solution {

    public SolutionQ2Batch(String DataPath) throws IOException, InterruptedException {
        super(DataPath);

        Query.Q2_DELETE_OVERLAY_GRAPH.setSolution(this);
        Query.Q2_INITIAL_OVERLAY_GRAPH.setSolution(this);
        Query.Q2_BATCH.setSolution(this);
    }

    @Override
    public String Initial() {
        runVoidQuery(Query.Q2_INITIAL_OVERLAY_GRAPH);
        String result = runReadQuery(Query.Q2_BATCH);

        return result;
    }

    @Override
    public String Update(File changes) {
        beforeUpdate(changes);

        runVoidQuery(Query.Q2_DELETE_OVERLAY_GRAPH);
        runVoidQuery(Query.Q2_INITIAL_OVERLAY_GRAPH);
        String result = runReadQuery(Query.Q2_BATCH);

        afterUpdate();

        return result;
    }
}
