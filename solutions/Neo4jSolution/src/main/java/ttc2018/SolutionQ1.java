package ttc2018;

import com.google.common.collect.ImmutableMap;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

import java.io.File;
import java.io.IOException;

import static ttc2018.Labels.Post;

public class SolutionQ1 extends Solution {
    private final Query initialQuery;
    private final Query afterNewCommentQuery;
    private final Query afterNewLikesQuery;

    public SolutionQ1(String DataPath, String toolName) throws IOException, InterruptedException {
        super(DataPath);

        Tool tool = Tool.valueOf(toolName);

        switch (tool) {
            case Neo4jSolution:
                initialQuery = Query.Q1_INITIAL;
                afterNewCommentQuery = Query.Q1_AFTER_NEW_COMMENT;
                afterNewLikesQuery = Query.Q1_AFTER_NEW_LIKES;
                break;
            case Neo4jSolution_materialized_root_post:
                initialQuery = Query.Q1_INITIAL_WITH_ROOT_POST;
                afterNewCommentQuery = Query.Q1_AFTER_NEW_COMMENT_WITH_ROOT_POST;
                afterNewLikesQuery = Query.Q1_AFTER_NEW_LIKES_WITH_ROOT_POST;
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    public enum Tool {
        Neo4jSolution,
        Neo4jSolution_materialized_root_post
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
        runAndCommitVoidQuery(initialQuery);

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

        runVoidQuery(tx, afterNewCommentQuery, ImmutableMap.of("commentVertex", comment));
    }

    @Override
    protected Relationship addLikesEdge(Transaction tx, String[] line) {
        Relationship likesEdge = super.addLikesEdge(tx, line);

        runVoidQuery(tx, afterNewLikesQuery, ImmutableMap.of("likesEdge", likesEdge));

        return likesEdge;
    }
}
