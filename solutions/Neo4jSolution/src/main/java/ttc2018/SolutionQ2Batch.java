package ttc2018;

import java.io.File;
import java.io.IOException;

public class SolutionQ2Batch extends Solution {

    public SolutionQ2Batch(String DataPath) throws IOException, InterruptedException {
        super(DataPath);

        Query.Q2_BATCH.setSolution(this);
    }

    @Override
    public String Initial() {
        String result = runReadQuery(Query.Q2_BATCH);

        return result;
    }

    @Override
    public String Update(File changes) {
        beforeUpdate(changes);

        String result = runReadQuery(Query.Q2_BATCH);

        afterUpdate();

        return result;
    }
}
