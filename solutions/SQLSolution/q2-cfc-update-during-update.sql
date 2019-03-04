WITH RECURSIVE -- note: though not the 1st query is the recursive one, the RECURSIVE keyword needs to be at the beginning
comment_friends_closed_stage0 AS (
    -- in order to maintain the transitive closure in comment_friends_closed
    -- we build on the transitive closure built so far and the new likes.
    -- We need the new likes because users that liked a specific comment
    -- are the vertices of the projected users graph for a comment
    SELECT commentid, head_userid, tail_userid
      FROM q2_comment_friends_closed
  UNION ALL
    SELECT l.commentid, l.userid AS head_userid, l.userid AS tail_userid
      FROM likes_d l  
)
, comment_friends_closed_stage1(commentid, head_userid, tail_userid) AS (
-- the transitive closure computed so far (reachability-only, no path is recorded)
-- is expanded by paths built from the new friendships
    SELECT commentid, head_userid, tail_userid
      FROM comment_friends_closed_stage0
  UNION
    SELECT cfc.commentid, cfc.head_userid, f.user2id as tail_userid
      FROM comment_friends_closed_stage1 cfc
         , comment_friends_d f
     WHERE 1=1
        -- join
       AND cfc.tail_userid = f.user1id
       AND cfc.commentid = f.commentid
)
, comment_friends_closed_stage2 AS (
-- transitive closure having the new friendships is then expanded using the
-- previous transitive closure stage
      SELECT distinct cfc.commentid, cfc.head_userid, r.tail_userid
        FROM comment_friends_closed_stage1 cfc
             inner join q2_comment_friends_closed r on (cfc.tail_userid = r.head_userid AND cfc.commentid = r.commentid)
             left join q2_comment_friends_closed s0 on (cfc.commentid = s0.commentid AND cfc.head_userid = s0.head_userid AND cfc.tail_userid = s0.tail_userid)
      WHERE s0.commentid IS NULL
    UNION
      SELECT commentid, head_userid, tail_userid
        FROM comment_friends_closed_stage1
)
INSERT INTO q2_comment_friends_closed(commentid, head_userid, tail_userid)
    select commentid, head_userid, tail_userid
      from comment_friends_closed_stage2 w
           left join q2_comment_friends_closed q using (commentid, head_userid, tail_userid)
     where q.commentid IS NULL
ON CONFLICT DO NOTHING
;
