MATCH (c:Comment)<-[:LIKES]-(u1:User)<-[:FRIEND_WHO_LIKES_COMMENT*0.. {commentId: c.id}]->(u2:User)
WITH c, u1, collect(DISTINCT u2) AS friends
  WHERE ALL(u2 IN friends WHERE id(u1) <= id(u2)) // <= to allow u1=u2
WITH c, friends, size(friends) AS componentSize
CREATE (c)-[:COMPONENT]->(comp:Component {size: componentSize})
FOREACH (u2 IN friends |
  CREATE (comp)-[:USER]->(u2)
)
WITH c, sum(componentSize * componentSize) AS score
SET c.score = score