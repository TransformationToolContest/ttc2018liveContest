package ttc2018;

import org.neo4j.graphdb.RelationshipType;

public enum RelationshipTypes implements RelationshipType {
    ROOT_POST,
    COMMENT_TO,
    LIKES,
    SUBMITTER,
    FRIEND,
    // extra types
    FRIEND_WHO_LIKES_COMMENT,
}