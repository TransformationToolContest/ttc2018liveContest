WITH $likesEdge AS l
MATCH (p:Post)<-[:COMMENT_TO*]-(:Comment)<-[l:LIKES]-(:User)
SET p.score = p.score + 1