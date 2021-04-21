UNWIND $friendEdgeIds AS friendEdgeId
MATCH (c:Comment)<-[:LIKES]-
      (u1:User)-[friendEdge]->(u2:User)
      -[:LIKES]->(c)
WHERE id(friendEdge) = friendEdgeId
SET c:Dirty
CREATE (u1)-[:FRIEND_WHO_LIKES_COMMENT {commentId: c.id}]->(u2)