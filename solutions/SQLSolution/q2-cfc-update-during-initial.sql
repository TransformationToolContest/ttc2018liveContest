WITH RECURSIVE -- note: though not the 1st query is the recursive one, the RECURSIVE keyword needs to be at the beginning
comment_friends_closed_initial AS (
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
, comment_friends_closed(commentid, head_userid, tail_userid) AS (
-- transitive closure (reachability-only, no path is recorded)
-- of friendship-subgraphs defined by comment likes

    SELECT commentid, head_userid, tail_userid
      FROM comment_friends_closed_initial
  UNION
    SELECT cfc.commentid, cfc.head_userid, f.user2id as tail_userid
      FROM comment_friends_closed cfc
         , comment_friends f
     WHERE 1=1
        -- join
       AND cfc.tail_userid = f.user1id
       AND cfc.commentid = f.commentid
)
INSERT INTO q2_comment_friends_closed(commentid, head_userid, tail_userid)
    select commentid, head_userid, tail_userid
      from comment_friends_closed w
           left join q2_comment_friends_closed q using (commentid, head_userid, tail_userid)
     where q.commentid IS NULL
;
