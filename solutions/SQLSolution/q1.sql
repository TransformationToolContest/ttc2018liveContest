WITH RECURSIVE
  -- calculate ancestors of all comments
  -- we don't care about filtering for rootposts, as it will be done in the last step
  -- by joining with posts, i.e. "posts left join comments_with_ancestors"
  comments_with_ancestors(id, ancestorid) AS (
     SELECT c.id, c.parentid AS ancestorid
       FROM comments c
   UNION
     SELECT cr.id, c.parentid AS ancestorid
       FROM comments_with_ancestors cr
          , comments c
      WHERE 1=1
         -- join
        AND cr.ancestorid = c.id
  )
-- we assume that no user puts more than 1 like on a single comment
select p.id, 10*count(distinct c.id) + count(l.userid) as score
  from posts p
       left join comments_with_ancestors c on (p.id = c.ancestorid)
       left join likes l on (c.id = l.commentid)
 where 1=1
 group by p.id, p.ts
 order by score desc, p.ts desc limit 3
;
