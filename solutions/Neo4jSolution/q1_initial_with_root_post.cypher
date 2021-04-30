MATCH (p:Post)<-[:COMMENT_TO*]-(c:Comment)
CREATE (p)<-[:ROOT_POST]-(c)
WITH p, c
OPTIONAL MATCH (c)<-[:LIKES]-(u:User)
WITH p, 10*count(DISTINCT c)+count(u) AS score
SET p.score = score

UNION ALL

MATCH (p:Post)
WHERE NOT (p)<-[:COMMENT_TO]-()
SET p.score = 0