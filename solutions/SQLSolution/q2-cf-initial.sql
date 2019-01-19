INSERT INTO comment_friends (status, commentid, user1id, user2id)
    SELECT 'I' AS status
         , l1.commentid, f.user1id, f.user2id
      FROM likes l1, likes l2
         , friends f
     WHERE 1=1
       AND l1.userid = f.user1id
       AND f.user2id = l2.userid
       AND l1.commentid = l2.commentid
;
