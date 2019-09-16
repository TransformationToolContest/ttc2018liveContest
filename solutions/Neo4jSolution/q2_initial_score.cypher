MATCH (c:Comment)
OPTIONAL MATCH (c)<-[:LIKES]-(u1:User)<-[:FRIEND_WHO_LIKES_COMMENT*0.. {commentId: c.id}]->(u2:User)
WITH c, u1, count(DISTINCT u2) AS size
WITH c, sum(size) AS score
SET c.score = score