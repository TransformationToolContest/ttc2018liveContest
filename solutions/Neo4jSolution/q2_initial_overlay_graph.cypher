MATCH (c:Comment)<-[:LIKES]-
      (u1:User)-[:FRIEND]->(u2:User)
      -[:LIKES]->(c)
CREATE (u1)-[:FRIEND_WHO_LIKES_COMMENT {commentId: c.id}]->(u2)