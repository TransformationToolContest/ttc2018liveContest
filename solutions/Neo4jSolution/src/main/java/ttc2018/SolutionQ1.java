package ttc2018;

import com.google.common.collect.ImmutableMap;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

import java.io.File;
import java.io.IOException;

import static ttc2018.Labels.Post;

public class SolutionQ1 extends Solution {

    public SolutionQ1(String DataPath) throws IOException, InterruptedException {
        super(DataPath);
    }

    @Override
    protected void addConstraintsAndIndicesInTx(Transaction tx) {
        super.addConstraintsAndIndicesInTx(tx);
        tx.schema()
                .indexFor(Post)
                .on(SUBMISSION_SCORE_PROPERTY)
                .create();
    }

    @Override
    public String Initial() {
        runAndCommitVoidQuery(Query.Q1_INITIAL);

        return runReadQuery(Query.Q1_RETRIEVE);
    }

    @Override
    public String Update(File changes) {
        beforeUpdate(changes);

        return runReadQuery(Query.Q1_RETRIEVE);
    }

    @Override
    protected void afterNewPost(Transaction tx, Node post, Node submitter) {
        super.afterNewPost(tx, post, submitter);

        post.setProperty(SUBMISSION_SCORE_PROPERTY, SUBMISSION_SCORE_DEFAULT);
    }

    @Override
    protected void afterNewComment(Transaction tx, Node comment, Node submitter, Node previousSubmission) {
        super.afterNewComment(tx, comment, submitter, previousSubmission);

        runVoidQuery(tx, Query.Q1_AFTER_NEW_COMMENT, ImmutableMap.of("commentVertex", comment));
    }

    @Override
    protected Relationship addLikesEdge(Transaction tx, String[] line) {
        Relationship likesEdge = super.addLikesEdge(tx, line);

        runVoidQuery(tx, Query.Q1_AFTER_NEW_LIKES, ImmutableMap.of("likesEdge", likesEdge));

        return likesEdge;
    }
}
