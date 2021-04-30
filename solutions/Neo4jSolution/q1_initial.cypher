MATCH (p:Post)
OPTIONAL MATCH (p)<-[:COMMENT_TO*]-(c:Comment)
OPTIONAL MATCH (c)<-[:LIKES]-(u:User)
WITH p, 10*count(DISTINCT c)+count(u) AS score
SET p.score = score