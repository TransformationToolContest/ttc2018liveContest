WITH $likesEdge AS l
MATCH (p:Post)<-[:ROOT_POST]-(:Comment)<-[l]-(:User)
SET p.score = p.score + 1