MATCH (c:Comment)
OPTIONAL MATCH (c)-[:COMPONENT]->(comp:Component)
WITH c, sum(comp.size * comp.size) AS score
SET c.score = score