WITH $commentVertex AS c
MATCH (p:Post)<-[:COMMENT_TO*]-(c:Comment)
SET p.score = p.score + 10