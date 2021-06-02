MATCH (c:Comment)
WHERE c.score >= 0 // to give a hint that this property is an integer and use index for it
RETURN c.id AS id, c.score AS score, c.timestamp AS timestamp
ORDER BY score DESC, timestamp DESC
LIMIT 3
