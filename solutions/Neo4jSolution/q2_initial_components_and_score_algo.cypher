MATCH (c:Comment)
WHERE (c)<-[:LIKES]-(:User)
CALL gds.wcc.stream({
    nodeQuery:
        'MATCH (c:Comment)<-[:LIKES]-(u:User)
        WHERE id(c)=' + id(c) + '
        RETURN id(u) as id',
    relationshipQuery:
        'MATCH (u1:User)<-[:FRIEND]->(u2:User)
        RETURN id(u1) as source, id(u2) as target',
    validateRelationships: false
  })
YIELD nodeId AS userId, componentId
WITH c, componentId, collect(gds.util.asNode(userId)) AS friends, count(userId) AS componentSize
CREATE (c)-[:COMPONENT]->(comp:Component {size: componentSize})
FOREACH (u IN friends |
  CREATE (comp)-[:USER]->(u)
)
WITH c, sum(componentSize * componentSize) AS score
SET c.score = score