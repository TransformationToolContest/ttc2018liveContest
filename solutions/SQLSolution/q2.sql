WITH RECURSIVE -- recursive stands here regardless of the fact that the 2nd subquery is the recursive one
  comment_friends (status, commentid, user1id, user2id) AS (
    SELECT 'I' AS status
         , l1.commentid, f.user1id, f.user2id
      FROM likes l1, likes l2
         , friends f
     WHERE 1=1
       AND l1.userid = f.user1id
       AND f.user2id = l2.userid
       AND l1.commentid = l2.commentid
)
, comment_friends_closed(commentid, head_userid, tail_userid) AS (
-- transitive closure (reachability-only, no path is recorded)
-- of friendship-subgraphs defined by comment likes

    -- start with the users that liked a specific comment.
    -- They are the vertices of the projected users graph for a comment
    SELECT l.commentid, l.userid AS head_userid, l.userid AS tail_userid
      FROM likes l
  UNION
    SELECT cfc.commentid, cfc.head_userid, f.user2id as tail_userid
      FROM comment_friends_closed cfc
         , comment_friends f
     WHERE 1=1
        -- join
       AND cfc.tail_userid = f.user1id
       AND cfc.commentid = f.commentid
)
, comment_components AS (
    SELECT commentid, tail_userid AS userid, min(head_userid) AS componentid
      FROM comment_friends_closed
     GROUP BY commentid, tail_userid
)
, comment_component_sizes AS (
    SELECT cc.commentid, cc.componentid, count(*) AS component_size
      FROM comment_components cc
     GROUP BY cc.commentid, cc.componentid
)
, scoring AS (
   -- Here we include all comments in order to have also those that have no likes
SELECT c.id AS commentid, coalesce(sum(ccs.component_size*ccs.component_size), 0) AS score
  FROM comments c left join comment_component_sizes ccs on (ccs.commentid = c.id)
 WHERE 1=1
 GROUP BY c.id, c.ts
 ORDER BY sum(ccs.component_size*ccs.component_size) DESC NULLS LAST, c.ts DESC LIMIT 3
)
SELECT *
  FROM scoring
;
