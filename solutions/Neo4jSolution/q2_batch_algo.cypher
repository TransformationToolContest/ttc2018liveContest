MATCH (c:Comment)
CALL gds.wcc.stream(
  'MATCH (c:Comment)<-[:LIKES]-(u:User)
   WHERE id(c)=' + id(c) + '
   RETURN id(u) as id',

  'MATCH (u1:User)-[:FRIEND]->(u2:User)
   RETURN id(u1) as source, id(u2) as target',

  {graph: 'cypher'})
  YIELD setId
WITH c, setId, count(setId) AS componentSize
WITH c, componentSize * componentSize AS componentSize_2
RETURN c.id AS id, sum(componentSize_2) AS score, c.timestamp
  ORDER BY score DESC, c.timestamp DESC
  LIMIT 3

UNION ALL

MATCH (c:Comment)
WHERE NOT (c)<-[:LIKES]-(:User)
RETURN c.id AS id, 0 AS score, c.timestamp
  ORDER BY c.timestamp DESC
  LIMIT 3