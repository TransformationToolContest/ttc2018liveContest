package ttc2018;

import java.io.File;
import java.io.IOException;

public class SolutionQ1Batch extends Solution {

    public SolutionQ1Batch(String DataPath) throws IOException, InterruptedException {
        super(DataPath);
    }

    @Override
    public String Initial() {
        return runReadQuery(Query.Q1_BATCH);
    }

    @Override
    public String Update(File changes) {
        beforeUpdate(changes);

        return runReadQuery(Query.Q1_BATCH);
    }
}
