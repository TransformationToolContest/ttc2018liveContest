MATCH (c:Comment)
OPTIONAL MATCH (c)<-[:LIKES]-(u1:User)<-[:FRIEND_WHO_LIKES_COMMENT*0.. {commentId: c.id}]->(u2:User)
WITH c, u1, count(DISTINCT u2) AS size
RETURN c.id AS id, sum(size) AS score, c.timestamp
ORDER BY score DESC, c.timestamp DESC
LIMIT 3