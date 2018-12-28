WITH RECURSIVE comment_friends_closed(commentid, head_userid, tail_userid) AS (
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
SELECT ccs.commentid, sum(component_size*component_size) AS score
  FROM comment_component_sizes ccs
     , comments c
 WHERE 1=1
    -- join
   AND ccs.commentid = c.id
 GROUP BY ccs.commentid, c.ts
 ORDER BY sum(component_size*component_size) DESC, c.ts DESC LIMIT 3
)
SELECT *
  FROM scoring
;
