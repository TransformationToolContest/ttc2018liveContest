MATCH (c:Comment)
WHERE (c)<-[:LIKES]-(:User)
CALL gds.wcc.stream({
    nodeQuery:
        'MATCH (c:Comment)<-[:LIKES]-(u:User)
        WHERE id(c)=' + id(c) + '
        RETURN id(u) as id',
    relationshipQuery:
        'MATCH (c:Comment)<-[:LIKES]-(u1:User)<-[:FRIEND]->(u2:User)-[:LIKES]->(c)
        WHERE id(c)=' + id(c) + '
        RETURN id(u1) as source, id(u2) as target'
  })
YIELD componentId
WITH c, componentId, count(componentId) AS componentSize
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