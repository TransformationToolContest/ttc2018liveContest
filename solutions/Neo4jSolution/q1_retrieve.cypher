MATCH (p:Post)
WHERE p.score >= 0 // to give a hint that this property is an integer and use index for it
RETURN p.id AS id, p.score AS score, p.timestamp AS timestamp
ORDER BY score DESC, timestamp DESC
LIMIT 3