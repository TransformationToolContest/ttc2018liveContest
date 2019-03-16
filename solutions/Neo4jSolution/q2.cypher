MATCH (c:Comment)
OPTIONAL MATCH (u1:User)-[:LIKES]->(c)
OPTIONAL MATCH p=(u1)-[:FRIEND*0..]-(u2:User), (u2)-[:LIKES]->(c)
WHERE all(u IN nodes(p) WHERE (u)-[:LIKES]->(c))
WITH c, u1, count(DISTINCT u2) AS size
RETURN c.id AS id, sum(size) AS score, c.timestamp
ORDER BY score DESC, c.timestamp DESC
LIMIT 3