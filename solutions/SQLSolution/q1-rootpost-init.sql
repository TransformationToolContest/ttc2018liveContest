WITH RECURSIVE
  -- calculate ancestors of comments
  comments_with_rootpost_stage1(id, ancestorid) AS (
     SELECT c.id, c.parentid AS ancestorid
       FROM comments c
   UNION
     SELECT cr.id, c.parentid AS ancestorid
       FROM comments_with_rootpost_stage1 cr
          , comments c
      WHERE 1=1
         -- join
        AND cr.ancestorid = c.id
  )
  -- calculate rootpost: when ancestor's id matches a post's id, then it is the root post
, comments_with_rootpost AS (
    SELECT c.id, c.ancestorid AS postid
      FROM comments_with_rootpost_stage1 c
         , posts p
     WHERE 1=1
       AND c.ancestorid = p.id
)
UPDATE comments c
   SET postid = cr.postid
  FROM comments_with_rootpost cr
 WHERE c.id = cr.id
;