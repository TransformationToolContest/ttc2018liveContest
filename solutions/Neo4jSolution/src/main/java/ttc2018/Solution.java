package ttc2018;

import com.google.common.collect.Iterators;
import org.neo4j.configuration.GraphDatabaseSettings;
import org.neo4j.dbms.api.DatabaseManagementService;
import org.neo4j.dbms.api.DatabaseManagementServiceBuilder;
import org.neo4j.exceptions.KernelException;
import org.neo4j.graphdb.*;
import org.neo4j.io.fs.FileUtils;
import org.neo4j.kernel.api.procedure.GlobalProcedures;
import org.neo4j.kernel.internal.GraphDatabaseAPI;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

public abstract class Solution implements AutoCloseable {
    DatabaseManagementService managementService;
    GraphDatabaseService graphDb;

    public abstract String Initial();

    /**
     * Update reading changes from CSV file
     */
    public abstract String Update(File changes);

    private final static String NEO4J_HOME = System.getenv("NEO4J_HOME");
    private final static Path DB_DIR = new File(NEO4J_HOME + "/data").toPath();
    private final static String LOAD_SCRIPT = "load-scripts/load.sh";

    private String DataPath;

    Solution(String DataPath) throws IOException, InterruptedException {
        this.DataPath = new File(DataPath).getCanonicalPath();
    }

    public GraphDatabaseService getDbConnection() {
        if (graphDb == null) {
            try {
                initializeDb();
            } catch (KernelException e) {
                throw new RuntimeException(e);
            }
        }

        return graphDb;
    }

    protected void initializeDb() throws KernelException {
        managementService = new DatabaseManagementServiceBuilder(new File(NEO4J_HOME).toPath())
                .setConfig(GraphDatabaseSettings.procedure_unrestricted, List.of("apoc.*", "gds.*"))
                .build();
        graphDb = managementService.database(GraphDatabaseSettings.DEFAULT_DATABASE_NAME);
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        if (managementService != null) {
            managementService.shutdown();
            managementService = null;
        }
    }

    // https://github.com/neo4j-contrib/neo4j-apoc-procedures/blob/3.5/src/test/java/apoc/util/TestUtil.java#L95
    public static void registerProcedure(GraphDatabaseService db, Class<?>... procedures) throws KernelException, KernelException {
        GlobalProcedures proceduresService = ((GraphDatabaseAPI) db).getDependencyResolver().resolveDependency(GlobalProcedures.class);
        for (Class<?> procedure : procedures) {
            proceduresService.registerProcedure(procedure, true);
            proceduresService.registerFunction(procedure, true);
            proceduresService.registerAggregationFunction(procedure, true);
        }
    }

    String runReadQuery(Query q) {
        return runReadQuery(q, Collections.emptyMap());
    }

    String runReadQuery(Query q, Map<String, Object> parameters) {
        try (Transaction tx = graphDb.beginTx()) {
            return runReadQuery(tx, q, parameters);
        }
    }

    protected static final int resultLimit = 3;

    String runReadQuery(Transaction tx, Query q, Map<String, Object> parameters) {
        try (Result rs = q.execute(tx, parameters)) {
            List<String> result = new ArrayList<>();

            int rowCount = 0;
            while (rs.hasNext()) {
                Map<String, Object> row = rs.next();
                String id = row.get(ID_COLUMN_NAME).toString();

                if (LiveContestDriver.ShowScoresForValidation) {
                    result.add(String.format("%1$s,%2$s", id, row.get(SCORE_COLUMN_NAME)));
                } else {
                    result.add(id);
                }

                ++rowCount;
                if (rowCount >= resultLimit)
                    break;
            }

            return String.join("|", result);
        }
    }

    void runAndCommitVoidQuery(Query q) {
        runAndCommitVoidQuery(q, Collections.emptyMap());
    }

    void runAndCommitVoidQuery(Query q, Map<String, Object> parameters) {
        try (Transaction tx = graphDb.beginTx()) {
            runVoidQuery(tx, q, parameters);
            tx.commit();
        }
    }

    void runVoidQuery(Transaction tx, Query q, Map<String, Object> parameters) {
        try (Result rs = q.execute(tx, parameters)) {
            rs.accept(row -> true);
        }
    }

    void loadData() throws IOException, InterruptedException {
        if (System.getenv("NEO4J_HOME") == null)
            throw new RuntimeException("$NEO4J_HOME is not defined.");

        // delete previous DB
        FileUtils.deleteDirectory(DB_DIR);

        ProcessBuilder pb = new ProcessBuilder(LOAD_SCRIPT);
        Map<String, String> env = pb.environment();
        env.put("NEO4J_DATA_DIR", DataPath);

        File log = new File("log.txt");
        pb.redirectErrorStream(true);
        pb.redirectOutput(ProcessBuilder.Redirect.appendTo(log));
        Process p = pb.start();
        p.waitFor();

        // DB initialization
        GraphDatabaseService dbConnection = getDbConnection();

        // add uniqueness constraints and indices
        try (Transaction tx = dbConnection.beginTx()) {
            addConstraintsAndIndicesInTx(tx);
            tx.commit();
        }

        try (Transaction tx = dbConnection.beginTx()) {
            tx.schema().awaitIndexesOnline(Long.MAX_VALUE, TimeUnit.NANOSECONDS);   // TODO: meaningful timeout
            tx.commit();
        }
    }

    protected void addConstraintsAndIndicesInTx(Transaction tx) {
        for (Labels label : Labels.values()) {
            tx.schema()
                    .constraintFor(label)
                    .assertPropertyIsUnique(NODE_ID_PROPERTY)
                    .create();
        }
    }

    void beforeUpdate(File changes) {
        try (Transaction tx = getDbConnection().beginTx()) {
            processChangeSet(tx, changes);
            tx.commit();
        }
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

    private void processChangeSet(Transaction tx, File changeSet) {
        try (Stream<String> stream = Files.lines(changeSet.toPath())) {

            stream.forEachOrdered(s -> {
                String[] line = s.split(Pattern.quote(SEPARATOR));
                switch (line[0]) {
                    case "Posts":
                    case COMMENTS_CHANGE_TYPE: {
                        long id = Long.parseLong(line[1]);

                        addSubmissionVertex(tx, line);
                        break;
                    }
                    case "Friends": {
                        // add edges only once
                        if (Long.parseLong(line[1]) <= Long.parseLong(line[2])) {
                            addFriendEdge(tx, line);
                        }
                        break;
                    }
                    case "Likes": {
                        addLikesEdge(tx, line);
                        break;
                    }
                    case "Users": {
                        addUserVertex(tx, line);
                        break;
                    }
                    default:
                        throw new RuntimeException("Invalid record type received from CSV input: " + line[0]);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected Node addSubmissionVertex(Transaction tx, String[] line) {
        long id = Long.parseLong(line[1]);
        String timestamp = line[2];
        String content = line[3];
        long submitterId = Long.parseLong(line[4]);

        Node submitter = findSingleNodeByIdProperty(tx, User, submitterId);

        Label[] labels = line[0].equals(COMMENTS_CHANGE_TYPE) ? CommentLabelSet : PostLabelSet;

        Node submission = tx.createNode(labels);
        submission.setProperty(NODE_ID_PROPERTY, id);
        submission.setProperty(SUBMISSION_TIMESTAMP_PROPERTY, timestamp);
        submission.setProperty(SUBMISSION_CONTENT_PROPERTY, content);

        submission.createRelationshipTo(submitter, SUBMITTER);

        if (line[0].equals(COMMENTS_CHANGE_TYPE)) {
            long previousSubmissionId = Long.parseLong(line[5]);

            Node previousSubmission = findSingleNodeByIdProperty(tx, Submission, previousSubmissionId);

            submission.createRelationshipTo(previousSubmission, COMMENT_TO);

            afterNewComment(tx, submission, submitter, previousSubmission);
        } else {
            afterNewPost(tx, submission, submitter);
        }

        return submission;
    }

    protected void afterNewComment(Transaction tx, Node comment, Node submitter, Node previousSubmission) {

    }

    protected void afterNewPost(Transaction tx, Node post, Node submitter) {

    }

    protected Relationship addFriendEdge(Transaction tx, String[] line) {
        return insertEdge(tx, line, FRIEND, User, User);
    }

    protected Relationship addLikesEdge(Transaction tx, String[] line) {
        return insertEdge(tx, line, LIKES, User, Comment);
    }

    protected Node addUserVertex(Transaction tx, String[] line) {
        long id = Long.parseLong(line[1]);
        String name = line[2];

        Node user = tx.createNode(User);
        user.setProperty(NODE_ID_PROPERTY, id);
        user.setProperty(USER_NAME_PROPERTY, name);
        return user;
    }

    private Node findSingleNodeByIdProperty(Transaction tx, Labels label, long id) {
        try (ResourceIterator<Node> nodes = tx.findNodes(label, NODE_ID_PROPERTY, id)) {
            return Iterators.getOnlyElement(nodes);
        }
    }

    private Relationship insertEdge(Transaction tx, String[] line, RelationshipTypes relationshipType, Labels sourceLabel, Labels targetLabel) {
        long sourceId = Long.parseLong(line[1]);
        long targetId = Long.parseLong(line[2]);

        Node source = findSingleNodeByIdProperty(tx, sourceLabel, sourceId);
        Node target = findSingleNodeByIdProperty(tx, targetLabel, targetId);

        return source.createRelationshipTo(target, relationshipType);
    }
}
