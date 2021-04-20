package ttc2018;

import org.neo4j.graphdb.RelationshipType;

public enum RelationshipTypes implements RelationshipType {
    COMMENT_TO,
    LIKES,
    SUBMITTER,
    FRIEND,
    // extra types
    ROOT_POST,
    FRIEND_WHO_LIKES_COMMENT,
    USER,
    COMPONENT,
}