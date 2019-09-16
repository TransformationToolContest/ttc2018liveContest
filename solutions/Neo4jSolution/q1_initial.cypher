MATCH (p:Post)
OPTIONAL MATCH (p)<-[:ROOT_POST]-(c:Comment)
OPTIONAL MATCH (c)<-[:LIKES]-(u:User)
WITH p, 10*count(DISTINCT c)+count(u) AS score
SET p.score = score