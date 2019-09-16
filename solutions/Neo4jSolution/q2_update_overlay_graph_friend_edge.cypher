UNWIND $friendEdges AS friendEdge
MATCH (c:Comment)<-[:LIKES]-
      (u1:User)-[friendEdge]->(u2:User)
      -[:LIKES]->(c)
SET c:Dirty
CREATE (u1)-[:FRIEND_WHO_LIKES_COMMENT {commentId: c.id}]->(u2)