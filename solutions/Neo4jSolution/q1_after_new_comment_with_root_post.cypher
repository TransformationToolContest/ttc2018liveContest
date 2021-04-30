WITH $commentVertex AS c
MATCH (c)-[:COMMENT_TO*]->()-[:ROOT_POST*0..1]->(p:Post)
WITH c, p LIMIT 1
CREATE (c)-[:ROOT_POST]->(p)
SET p.score = p.score + 10