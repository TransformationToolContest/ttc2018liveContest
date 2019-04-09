package ttc2018;

import com.google.common.collect.Iterators;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.io.fs.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static ttc2018.Labels.*;
import static ttc2018.Query.ID_COLUMN_NAME;
import static ttc2018.Query.SCORE_COLUMN_NAME;
import static ttc2018.RelationshipTypes.*;

public abstract class Solution {
    // see: getDbConnection()
    GraphDatabaseService graphDb;

    public abstract String Initial();

    /**
     * Update reading changes from CSV file
     */
    public abstract String Update(File changes);

    private final static File DB_DIR = new File("db-dir/graph.db");
    private final static String LOAD_SCRIPT = "load-scripts/load.sh";

    private String DataPath;

    Solution(String DataPath) throws IOException, InterruptedException {
        this.DataPath = new File(DataPath).getCanonicalPath();
    }

    public GraphDatabaseService getDbConnection() {
        if (graphDb == null) {
            graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(DB_DIR);
            Runtime.getRuntime().addShutdownHook(new Thread(graphDb::shutdown));
        }

        return graphDb;
    }

    String runReadQuery(Query q) {
        return runReadQuery(q, Collections.emptyMap());
    }

    String runReadQuery(Query q, Map<String, Object> parameters) {
        List<String> result = new ArrayList<>();

        try (Result rs = q.execute(parameters)) {
            for (Map<String, Object> row : org.neo4j.helpers.collection.Iterators.asIterable(rs)) {
                String id = row.get(ID_COLUMN_NAME).toString();

                if (LiveContestDriver.ShowScoresForValidation) {
                    result.add(String.format("%1$s,%2$s", id, row.get(SCORE_COLUMN_NAME)));
                } else {
                    result.add(id);
                }
            }

            return String.join("|", result);
        }
    }

    void runVoidQuery(Query q) {
        runVoidQuery(q, Collections.emptyMap());
    }

    void runVoidQuery(Query q, Map<String, Object> parameters) {
        try (Result rs = q.execute(parameters)) {
            rs.hasNext();
        }
    }

    void loadData() throws IOException, InterruptedException {
        if (System.getenv("NEO4J_HOME") == null)
            throw new RuntimeException("$NEO4J_HOME is not defined.");

        // delete previous DB
        FileUtils.deleteRecursively(DB_DIR);

        ProcessBuilder pb = new ProcessBuilder(LOAD_SCRIPT);
        Map<String, String> env = pb.environment();
        env.put("NEO4J_DATA_DIR", DataPath);
        env.put("NEO4J_DB_DIR", DB_DIR.getCanonicalPath());

        File log = new File("log.txt");
        pb.redirectErrorStream(true);
        pb.redirectOutput(ProcessBuilder.Redirect.appendTo(log));
        Process p = pb.start();
        p.waitFor();

        // DB initialization
        GraphDatabaseService dbConnection = getDbConnection();

        // add uniqueness constraints and indices
        try (Transaction tx = dbConnection.beginTx()) {
            addConstraintsAndIndicesInTx(dbConnection);

            tx.success();
        }

        try (Transaction tx = dbConnection.beginTx()) {
            // TODO: meaningful timeout
            dbConnection.schema().awaitIndexesOnline(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        }
    }

    protected void addConstraintsAndIndicesInTx(GraphDatabaseService dbConnection) {
        for (Labels label : Labels.values()) {
            dbConnection.schema()
                    .constraintFor(label)
                    .assertPropertyIsUnique(NODE_ID_PROPERTY)
                    .create();
        }
    }

    void beforeUpdate(File changes) {
        processChangeSet(changes);
    }

    public static final String SEPARATOR = "|";
    public static final String COMMENTS_CHANGE_TYPE = "Comments";
    public static final String NODE_ID_PROPERTY = "id";
    public static final String USER_NAME_PROPERTY = "name";
    public static final String SUBMISSION_TIMESTAMP_PROPERTY = "timestamp";
    public static final String SUBMISSION_CONTENT_PROPERTY = "content";
    public static final String SUBMISSION_SCORE_PROPERTY = "score";
    public static final long SUBMISSION_SCORE_DEFAULT = 0L;
    public static final String FRIEND_OVERLAY_EDGE_COMMENT_ID_PROPERTY = "commentId";

    public void processChangeSet(File changeSet) {
        try (Stream<String> stream = Files.lines(changeSet.toPath());
             Transaction tx = graphDb.beginTx()) {

            stream.forEachOrdered(s -> {
                String[] line = s.split(Pattern.quote(SEPARATOR));
                switch (line[0]) {
                    case "Posts":
                    case COMMENTS_CHANGE_TYPE: {
                        long id = Long.parseLong(line[1]);

                        // TODO: remove workaround of duplicate nodes in changeset
                        if (!nodeByIdPropertyExists(Submission, id)) {
                            addSubmissionVertex(line);
                        }
                        break;
                    }
                    case "Friends": {
                        // add edges only once
                        if (Long.parseLong(line[1]) <= Long.parseLong(line[2])) {
                            addFriendEdge(line);
                        }
                        break;
                    }
                    case "Likes": {
                        addLikesEdge(line);
                        break;
                    }
                    case "Users": {
                        addUserVertex(line);
                        break;
                    }
                    default:
                        throw new RuntimeException("Invalid record type received from CSV input: " + line[0]);
                }
            });

            tx.success();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected Node addSubmissionVertex(String[] line) {
        long id = Long.parseLong(line[1]);
        String timestamp = line[2];
        String content = line[3];
        long submitterId = Long.parseLong(line[4]);

        Node submitter = findSingleNodeByIdProperty(User, submitterId);

        Label[] labels = line[0].equals(COMMENTS_CHANGE_TYPE) ? CommentLabelSet : PostLabelSet;

        Node submission = graphDb.createNode(labels);
        submission.setProperty(NODE_ID_PROPERTY, id);
        submission.setProperty(SUBMISSION_TIMESTAMP_PROPERTY, timestamp);
        submission.setProperty(SUBMISSION_CONTENT_PROPERTY, content);

        submission.createRelationshipTo(submitter, SUBMITTER);

        if (line[0].equals(COMMENTS_CHANGE_TYPE)) {
            long previousSubmissionId = Long.parseLong(line[5]);
            long rootPostId = Long.parseLong(line[6]);

            Node previousSubmission = findSingleNodeByIdProperty(Submission, previousSubmissionId);
            Node rootPost = findSingleNodeByIdProperty(Post, rootPostId);

            submission.createRelationshipTo(previousSubmission, COMMENT_TO);
            submission.createRelationshipTo(rootPost, ROOT_POST);

            afterNewComment(submission, submitter, previousSubmission, rootPost);
        } else {
            afterNewPost(submission, submitter);
        }

        return submission;
    }

    protected void afterNewComment(Node comment, Node submitter, Node previousSubmission, Node rootPost) {

    }

    protected void afterNewPost(Node post, Node submitter) {

    }

    protected Relationship addFriendEdge(String[] line) {
        return insertEdge(line, FRIEND, User, User);
    }

    protected Relationship addLikesEdge(String[] line) {
        return insertEdge(line, LIKES, User, Comment);
    }

    protected Node addUserVertex(String[] line) {
        long id = Long.parseLong(line[1]);
        String name = line[2];

        Node user = graphDb.createNode(User);
        user.setProperty(NODE_ID_PROPERTY, id);
        user.setProperty(USER_NAME_PROPERTY, name);

        return user;
    }

    private Node findSingleNodeByIdProperty(Labels label, long id) {
        try (ResourceIterator<Node> nodes = graphDb.findNodes(label, NODE_ID_PROPERTY, id)) {
            return Iterators.getOnlyElement(nodes);
        }
    }

    private boolean nodeByIdPropertyExists(Labels label, long id) {
        try (ResourceIterator<Node> nodes = graphDb.findNodes(label, NODE_ID_PROPERTY, id)) {
            return nodes.hasNext();
        }
    }

    private Relationship insertEdge(String[] line, RelationshipTypes relationshipType, Labels sourceLabel, Labels targetLabel) {
        long sourceId = Long.parseLong(line[1]);
        long targetId = Long.parseLong(line[2]);

        Node source = findSingleNodeByIdProperty(sourceLabel, sourceId);
        Node target = findSingleNodeByIdProperty(targetLabel, targetId);

        return source.createRelationshipTo(target, relationshipType);
    }

    void afterUpdate() {
    }
}
