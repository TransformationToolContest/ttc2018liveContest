package ttc2018;

import apoc.create.Create;
import apoc.path.PathExplorer;
import apoc.periodic.Periodic;
import apoc.refactor.GraphRefactoring;
import com.google.common.collect.ImmutableMap;
import org.neo4j.exceptions.KernelException;
import org.neo4j.graphalgo.functions.AsNodeFunc;
import org.neo4j.graphalgo.wcc.WccStreamProc;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import static ttc2018.Labels.Comment;

public class SolutionQ2 extends Solution {

    private Tool tool;

    public SolutionQ2(String DataPath, String toolName) throws IOException, InterruptedException {
        super(DataPath);

        tool = Tool.valueOf(toolName);
    }

    public enum Tool {
        Neo4jSolution_overlay_graph(true, false),
        Neo4jSolution_explicit_component(false, true),
        Neo4jSolution(false, true), // previously Neo4jSolution_explicit_component_periodic
        Neo4jSolution_explicit_component_algo(false, true),
        ;

        final boolean maintainOverlayGraph;
        final boolean maintainExplicitComponent;

        Tool(boolean maintainOverlayGraph, boolean maintainExplicitComponent) {
            this.maintainOverlayGraph = maintainOverlayGraph;
            this.maintainExplicitComponent = maintainExplicitComponent;
        }
    }

    @Override
    protected void initializeDb() {
        super.initializeDb();

        try {
            if (tool.maintainExplicitComponent)
                registerProcedure(graphDb, GraphRefactoring.class);

            switch (tool) {
                case Neo4jSolution_explicit_component_algo:
                    registerProcedure(graphDb, WccStreamProc.class, AsNodeFunc.class);
                    break;
                case Neo4jSolution:
                    registerProcedure(graphDb, Create.class, Periodic.class, PathExplorer.class);
                    break;
            }
        } catch (KernelException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void addConstraintsAndIndicesInTx(Transaction tx) {
        super.addConstraintsAndIndicesInTx(tx);

        tx.schema()
                .indexFor(Comment)
                .on(SUBMISSION_SCORE_PROPERTY)
                .create();
        // note: cannot create index on commentId property of FRIEND_WHO_LIKES_COMMENT edge
    }

    @Override
    public String Initial() {
        switch (tool) {
            case Neo4jSolution_overlay_graph:
                runAndCommitVoidQuery(Query.Q2_INITIAL_OVERLAY_GRAPH);

                runAndCommitVoidQuery(Query.Q2_INITIAL_SCORE);
                break;
            case Neo4jSolution_explicit_component:
                runAndCommitVoidQuery(Query.Q2_INITIAL_OVERLAY_GRAPH);

                runAndCommitVoidQuery(Query.Q2_INITIAL_COMPONENTS_AND_SCORE);
                runAndCommitVoidQuery(Query.Q2_INITIAL_ZERO_SCORE);
                break;
            case Neo4jSolution_explicit_component_algo:
                runAndCommitVoidQuery(Query.Q2_INITIAL_COMPONENTS_AND_SCORE_ALGO);
                runAndCommitVoidQuery(Query.Q2_INITIAL_ZERO_SCORE);
                break;
            case Neo4jSolution:
                runAndCommitVoidQuery(Query.Q2_INITIAL_DYNAMIC_LIKES_LABELS);

                try (Transaction tx = getDbConnection().beginTx()) {
                    Map batchErrors = (Map) Query.Q2_INITIAL_COMPONENTS_PERIODIC.execute(tx, Collections.emptyMap())
                            .columnAs("batchErrors")
                            .stream().collect(Collectors.toList())
                            .get(0);
                    if (!batchErrors.isEmpty())
                        throw new RuntimeException(batchErrors.toString(), new Exception());
                    tx.commit();
                }

                runAndCommitVoidQuery(Query.Q2_INITIAL_SCORE_FROM_EXPLICIT_COMPONENTS);
                break;
            default:
                throw new IllegalArgumentException();
        }

        return runReadQuery(Query.Q2_RETRIEVE);
    }

    @Override
    protected void afterNewComment(Transaction tx, Node comment, Node submitter, Node previousSubmission, Node rootPost) {
        super.afterNewComment(tx, comment, submitter, previousSubmission, rootPost);

        comment.setProperty(SUBMISSION_SCORE_PROPERTY, SUBMISSION_SCORE_DEFAULT);
    }

    @Override
    protected Relationship addFriendEdge(Transaction tx, String[] line) {
        Relationship friendEdge = super.addFriendEdge(tx, line);
        newFriendEdgeIds.add(friendEdge.getId());

        if (tool.maintainExplicitComponent) {
            runVoidQuery(tx, Query.Q2_MERGE_COMPONENTS_AFTER_FRIEND_EDGE, ImmutableMap.of("friendEdgeId", friendEdge.getId()));
        }

        return friendEdge;
    }

    @Override
    protected Relationship addLikesEdge(Transaction tx, String[] line) {
        Relationship likesEdge = super.addLikesEdge(tx, line);
        newLikesEdgeIds.add(likesEdge.getId());

        if (tool.maintainExplicitComponent) {
            runVoidQuery(tx, Query.Q2_MERGE_COMPONENTS_AFTER_LIKES_EDGE, ImmutableMap.of("likesEdgeId", likesEdge.getId()));
        }

        return likesEdge;
    }

    private ArrayList<Long> newFriendEdgeIds;
    private ArrayList<Long> newLikesEdgeIds;

    @Override
    public String Update(File changes) {
        newFriendEdgeIds = new ArrayList<>();
        newLikesEdgeIds = new ArrayList<>();

        beforeUpdate(changes);

        if (tool.maintainOverlayGraph) {
            if (!newFriendEdgeIds.isEmpty())
                runAndCommitVoidQuery(Query.Q2_UPDATE_OVERLAY_GRAPH_FRIEND_EDGE, ImmutableMap.of("friendEdgeIds", newFriendEdgeIds));
            if (!newLikesEdgeIds.isEmpty())
                runAndCommitVoidQuery(Query.Q2_UPDATE_OVERLAY_GRAPH_LIKES_EDGE, ImmutableMap.of("likesEdgeIds", newLikesEdgeIds));

            runAndCommitVoidQuery(Query.Q2_RECALCULATE_SCORE);
        }

        return runReadQuery(Query.Q2_RETRIEVE);
    }
}
