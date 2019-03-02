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
-- Here we include all comments in order to have also those that have no likes
SELECT c.id AS commentid, coalesce(sum(ccs.component_size*ccs.component_size), 0) AS score
  FROM comments c left join comment_component_sizes ccs on (ccs.commentid = c.id)
 WHERE 1=1
 GROUP BY c.id, c.ts
 ORDER BY sum(ccs.component_size*ccs.component_size) DESC NULLS LAST, c.ts DESC LIMIT 3
;
