MATCH (c)<-[:LIKES]-(u:User)
WITH c, collect(u) AS users
CALL apoc.create.addLabels(users, ['Likes_' + c.id]) YIELD node
RETURN count(*)