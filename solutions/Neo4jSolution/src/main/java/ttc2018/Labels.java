package ttc2018;

import org.neo4j.graphdb.Label;

public enum Labels implements Label {
    Submission,
    Post,
    Comment,
    User;

    public static final Label[] PostLabelSet = new Label[]{Submission, Post};
    public static final Label[] CommentLabelSet = new Label[]{Submission, Comment};
}
