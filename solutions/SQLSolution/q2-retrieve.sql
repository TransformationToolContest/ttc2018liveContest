WITH comment_components AS (
    SELECT commentid, tail_userid AS userid, min(head_userid) AS componentid
      FROM q2_comment_friends_closed
     GROUP BY commentid, tail_userid
)
, comment_component_sizes AS (
    SELECT cc.commentid, cc.componentid, count(*) AS component_size
      FROM comment_components cc
     GROUP BY cc.commentid, cc.componentid
)
SELECT ccs.commentid, sum(component_size*component_size) AS score
  FROM comment_component_sizes ccs
     , comments c
 WHERE 1=1
    -- join
   AND ccs.commentid = c.id
 GROUP BY ccs.commentid, c.ts
 ORDER BY sum(component_size*component_size) DESC, c.ts DESC LIMIT 3
;
