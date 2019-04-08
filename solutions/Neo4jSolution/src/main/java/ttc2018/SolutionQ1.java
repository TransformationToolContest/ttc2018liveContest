package ttc2018;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

import java.io.File;
import java.io.IOException;

import static org.neo4j.graphdb.Direction.OUTGOING;
import static ttc2018.Labels.Post;
import static ttc2018.RelationshipTypes.ROOT_POST;

public class SolutionQ1 extends Solution {

    public SolutionQ1(String DataPath) throws IOException, InterruptedException {
        super(DataPath);

        Query.Q1_INITIAL.setSolution(this);
        Query.Q1_RETRIEVE.setSolution(this);
    }

    @Override
    protected void addConstraintsAndIndicesInTx(GraphDatabaseService dbConnection) {
        super.addConstraintsAndIndicesInTx(dbConnection);

        dbConnection.schema()
                .indexFor(Post)
                .on(SUBMISSION_SCORE_PROPERTY)
                .create();
    }

    @Override
    public String Initial() {
        runVoidQuery(Query.Q1_INITIAL);
        String result = runReadQuery(Query.Q1_RETRIEVE);

        return result;
    }

    @Override
    public String Update(File changes) {
        beforeUpdate(changes);

        String result = runReadQuery(Query.Q1_RETRIEVE);

        afterUpdate();

        return result;
    }

    @Override
    protected void afterNewPost(Node post, Node submitter) {
        super.afterNewPost(post, submitter);

        post.setProperty(SUBMISSION_SCORE_PROPERTY, 0L);
    }

    @Override
    protected void afterNewComment(Node comment, Node submitter, Node previousSubmission, Node rootPost) {
        super.afterNewComment(comment, submitter, previousSubmission, rootPost);

        rootPost.setProperty(SUBMISSION_SCORE_PROPERTY, (Long) rootPost.getProperty(SUBMISSION_SCORE_PROPERTY) + 10);
    }

    @Override
    protected Relationship addLikesEdge(String[] line) {
        Relationship likesEdge = super.addLikesEdge(line);

        Node comment = likesEdge.getEndNode();
        Node rootPost = comment.getSingleRelationship(ROOT_POST, OUTGOING).getEndNode();

        rootPost.setProperty(SUBMISSION_SCORE_PROPERTY, (Long) rootPost.getProperty(SUBMISSION_SCORE_PROPERTY) + 1);

        return likesEdge;
    }
}
