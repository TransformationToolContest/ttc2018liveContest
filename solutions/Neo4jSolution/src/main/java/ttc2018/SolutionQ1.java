package ttc2018;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

import java.io.File;
import java.io.IOException;

import static org.neo4j.graphdb.Direction.OUTGOING;
import static ttc2018.Labels.Post;
import static ttc2018.RelationshipTypes.ROOT_POST;

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
        String result = runReadQuery(Query.Q1_RETRIEVE);

        return result;
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
    protected void afterNewComment(Transaction tx, Node comment, Node submitter, Node previousSubmission, Node rootPost) {
        super.afterNewComment(tx, comment, submitter, previousSubmission, rootPost);

        rootPost.setProperty(SUBMISSION_SCORE_PROPERTY, (Long) rootPost.getProperty(SUBMISSION_SCORE_PROPERTY) + 10);
    }

    @Override
    protected Relationship addLikesEdge(Transaction tx, String[] line) {
        Relationship likesEdge = super.addLikesEdge(tx, line);

        Node comment = likesEdge.getEndNode();
        Node rootPost = comment.getSingleRelationship(ROOT_POST, OUTGOING).getEndNode();

        rootPost.setProperty(SUBMISSION_SCORE_PROPERTY, (Long) rootPost.getProperty(SUBMISSION_SCORE_PROPERTY) + 1);

        return likesEdge;
    }
}
