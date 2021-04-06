WITH RECURSIVE
comment_friends_closed_init(commentid, head_userid, tail_userid) AS (
-- transitive closure (reachability-only, no path is recorded)
-- of friendship-subgraphs defined by comment likes

    -- start with the users that liked a specific comment.
    -- They are the vertices of the projected users graph for a comment
    SELECT l.commentid, l.userid AS head_userid, l.userid AS tail_userid
      FROM likes l
  UNION
    -- expand the closure w/ the edges of the projected graph,
    -- which is stored in comment_friends table
    SELECT cfc.commentid, cfc.head_userid, f.user2id as tail_userid
      FROM comment_friends_closed_init cfc
         , comment_friends f
     WHERE 1=1
        -- join
       AND cfc.tail_userid = f.user1id
       AND cfc.commentid = f.commentid
)
INSERT INTO comment_friends_closed(commentid, head_userid, tail_userid)
    select commentid, head_userid, tail_userid
      from comment_friends_closed_init w
           left join comment_friends_closed q using (commentid, head_userid, tail_userid)
     where q.commentid IS NULL
;
