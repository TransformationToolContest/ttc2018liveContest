MATCH (p:Post)
OPTIONAL MATCH (p)<-[:ROOT_POST]-(c:Comment)
OPTIONAL MATCH (c)<-[:LIKES]-(u:User)
RETURN p.id AS id, 10*count(DISTINCT c)+count(u) AS score, p.timestamp AS timestamp
ORDER BY score DESC, timestamp DESC
LIMIT 3