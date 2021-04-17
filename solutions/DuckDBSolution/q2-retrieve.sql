WITH comment_components AS (
    SELECT commentid, head_userid AS userid, min(tail_userid) AS componentid
      FROM comment_friends_closed
     GROUP BY commentid, head_userid
)
, comment_component_sizes AS (
    SELECT cc.commentid, cc.componentid, count(*) AS component_size
      FROM comment_components cc
     GROUP BY cc.commentid, cc.componentid
)
-- consider all comments including those without likes
SELECT c.id AS commentid, coalesce( sum( power(ccs.component_size, 2) ), 0) AS score
  FROM comments c left join comment_component_sizes ccs on (ccs.commentid = c.id)
 WHERE 1=1
 GROUP BY c.id, c.ts
 ORDER BY sum( power(ccs.component_size, 2) ) DESC NULLS LAST, c.ts DESC LIMIT 3
;
