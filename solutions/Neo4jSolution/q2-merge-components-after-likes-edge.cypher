WITH $likesEdge AS likesEdge
MATCH (c:Comment)<-[likesEdge]-(u:User)
CREATE (c)-[:COMPONENT]->(uComp:Component {size: 1})-[:USER]->(u)
WITH c, uComp, u
OPTIONAL MATCH (c)-[:COMPONENT]->(comp2:Component) // OPTIONAL: to go forth if there is no such component
  WHERE (comp2)-[:USER]->(:User)<-[:FRIEND]->(u) // should I add LIKE edge as constraint?
WITH c,
     uComp + collect(comp2) AS components,
     1 + sum(comp2.size) AS newCompSize,
     sum(comp2.size * comp2.size) AS scoreDecrease
// mergeRels: to avoid parallel COMPONENT edges
CALL apoc.refactor.mergeNodes(components, {mergeRels: true}) YIELD node AS newComp
SET newComp.size = newCompSize,
    c.score = c.score - scoreDecrease + newCompSize*newCompSize