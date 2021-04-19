package ttc2018;

import java.io.File;
import java.io.IOException;

public class SolutionQ1Batch extends Solution {

    public SolutionQ1Batch(String DataPath) throws IOException, InterruptedException {
        super(DataPath);
    }

    @Override
    public String InitialInTX() {
        String result = runReadQuery(Query.Q1_BATCH);

        return result;
    }

    @Override
    public String UpdateInTx(File changes) {
        beforeUpdate(changes);

        String result = runReadQuery(Query.Q1_BATCH);

        afterUpdate();

        return result;
    }
}
