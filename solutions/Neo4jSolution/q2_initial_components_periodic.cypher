CALL apoc.periodic.commit("
MATCH (c:Comment)<-[:LIKES]-(u1:User)
WITH c, min(u1) AS u1
CREATE (c)-[:COMPONENT]->(comp:Component)
WITH c, u1, comp
CALL apoc.path.subgraphNodes(u1, {labelFilter: 'Likes_' + c.id,
    relationshipFilter: 'FRIEND'}) YIELD node AS u2
CREATE (comp)-[:USER]->(u2)
WITH c, comp, u2
MATCH (c)<-[l:LIKES]-(u2)
DELETE l
WITH c, comp, count(*) AS componentSize
SET comp.size = componentSize
RETURN count(*)
// limit - to bypass mandatory limit
")