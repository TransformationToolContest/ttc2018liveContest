MATCH (c:Comment)
  WHERE NOT (c)<-[:LIKES]-(:User)
SET c.score = 0
