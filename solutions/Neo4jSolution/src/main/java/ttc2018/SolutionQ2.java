package ttc2018;

import com.google.common.collect.ImmutableMap;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static ttc2018.Labels.Comment;

public class SolutionQ2 extends Solution {

    private Tool tool;

    public SolutionQ2(String DataPath, String toolName) throws IOException, InterruptedException {
        super(DataPath);

        tool = Tool.valueOf(toolName);

        Query.Q2_INITIAL_OVERLAY_GRAPH.setSolution(this);
        Query.Q2_INITIAL_SCORE.setSolution(this);
        Query.Q2_UPDATE_OVERLAY_GRAPH_FRIEND_EDGE.setSolution(this);
        Query.Q2_UPDATE_OVERLAY_GRAPH_LIKES_EDGE.setSolution(this);
        Query.Q2_RECALCULATE_SCORE.setSolution(this);
        Query.Q2_RECALCULATE_SCORE_V2.setSolution(this);
        Query.Q2_RETRIEVE.setSolution(this);
    }

    public enum Tool {
        Neo4jSolution,
        Neo4jSolution_new_score,
    }

    @Override
    protected void addConstraintsAndIndicesInTx(GraphDatabaseService dbConnection) {
        super.addConstraintsAndIndicesInTx(dbConnection);

        dbConnection.schema()
                .indexFor(Comment)
                .on(SUBMISSION_SCORE_PROPERTY)
                .create();

        // note: cannot create index on commentId property of FRIEND_WHO_LIKES_COMMENT edge
    }

    @Override
    public String Initial() {
        runVoidQuery(Query.Q2_INITIAL_OVERLAY_GRAPH);
        runVoidQuery(Query.Q2_INITIAL_SCORE);
        String result = runReadQuery(Query.Q2_RETRIEVE);

        return result;
    }

    @Override
    protected void afterNewComment(Node comment, Node submitter, Node previousSubmission, Node rootPost) {
        super.afterNewComment(comment, submitter, previousSubmission, rootPost);

        comment.setProperty(SUBMISSION_SCORE_PROPERTY, SUBMISSION_SCORE_DEFAULT);
    }

    @Override
    protected Relationship addFriendEdge(String[] line) {
        Relationship friendEdge = super.addFriendEdge(line);
        newFriendEdges.add(friendEdge);

        return friendEdge;
    }

    @Override
    protected Relationship addLikesEdge(String[] line) {
        Relationship likesEdge = super.addLikesEdge(line);
        newLikesEdges.add(likesEdge);

        return likesEdge;
    }

    private ArrayList<Relationship> newFriendEdges;
    private ArrayList<Relationship> newLikesEdges;

    @Override
    public String Update(File changes) {
        newFriendEdges = new ArrayList<>();
        newLikesEdges = new ArrayList<>();

        beforeUpdate(changes);

        if (!newFriendEdges.isEmpty())
            runVoidQuery(Query.Q2_UPDATE_OVERLAY_GRAPH_FRIEND_EDGE, ImmutableMap.of("friendEdges", newFriendEdges));
        if (!newLikesEdges.isEmpty())
            runVoidQuery(Query.Q2_UPDATE_OVERLAY_GRAPH_LIKES_EDGE, ImmutableMap.of("likesEdges", newLikesEdges));

        switch (tool) {
            case Neo4jSolution:
                runVoidQuery(Query.Q2_RECALCULATE_SCORE);
                break;
            case Neo4jSolution_new_score:
                runVoidQuery(Query.Q2_RECALCULATE_SCORE_V2);
                break;
            default:
                throw new IllegalArgumentException();
        }
        String result = runReadQuery(Query.Q2_RETRIEVE);

        afterUpdate();

        return result;
    }
}
