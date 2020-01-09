MATCH (c:Comment)
CALL algo.unionFind.stream(
'MATCH (c:Comment)<-[:LIKES]-(u:User)
 WHERE id(c)=' + id(c) + '
   RETURN id(u) as id',

'MATCH (u1:User)-[:FRIEND]->(u2:User)
 RETURN id(u1) as source, id(u2) as target',

{graph: 'cypher'})
YIELD nodeId AS userId, setId
WITH c, setId, collect(algo.asNode(userId)) AS friends, count(userId) AS componentSize
CREATE (c)-[:COMPONENT]->(comp:Component {size: componentSize})
FOREACH (u IN friends |
  CREATE (comp)-[:USER]->(u)
)
WITH c, sum(componentSize * componentSize) AS score
SET c.score = score