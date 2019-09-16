WITH $friendEdge AS friendEdge
MATCH (comp1:Component)-[:USER]->(u1:User)-[friendEdge]->(u2:User)<-[:USER]-(comp2:Component),
// comp1 <> comp2, because COMPONENT edges are different
      (comp1)<-[:COMPONENT]-(c:Comment)-[:COMPONENT]->(comp2)
WITH c, comp1, comp2,
     comp1.size AS comp1Size,
     comp2.size AS comp2Size,
     comp1.size + comp2.size AS newCompSize
// mergeRels: to avoid parallel COMPONENT edges
CALL apoc.refactor.mergeNodes([comp1, comp2], {mergeRels: true}) YIELD node AS newComp
SET newComp.size = newCompSize,
    c.score = c.score - comp1Size*comp1Size - comp2Size*comp2Size + newCompSize*newCompSize