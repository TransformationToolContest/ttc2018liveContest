ALTER TABLE posts ADD PRIMARY KEY (id, status);
ALTER TABLE comments ADD PRIMARY KEY (id, status);
ALTER TABLE users ADD PRIMARY KEY (id, status);

ALTER TABLE q1_result ADD PRIMARY KEY (postid);

-- re-order friends on the storage level by user1id.
-- This order is not hold after writes to the table.
CREATE INDEX friends_user1id ON friends (user1id);
CLUSTER friends_i USING friends_i_user1id_idx;

vacuum analyze;

-- additional indexes
CREATE UNIQUE INDEX friends_user1id_user2id ON friends (user1id, user2id, status);
CREATE UNIQUE INDEX friends_user2id_user1id ON friends (user2id, user1id, status);
CREATE UNIQUE INDEX likes_commentid_userid ON likes (commentid, userid, status);

-- create index on foreign keys
CREATE INDEX posts_submitterid ON posts (submitterid);
CREATE INDEX comments_submitterid ON comments (submitterid);
CREATE INDEX comments_previousid ON comments (previousid);
CREATE INDEX comments_postid ON comments (postid);
CREATE INDEX friends_user2id ON friends (user2id);
CREATE INDEX likes_userid ON likes (userid);
CREATE INDEX likes_commentid ON likes (commentid);

/* -- disabled in favor of programmatic maintenance from the SQLSolution's code
-- populate comment_friends table
INSERT INTO comment_friends (status, commentid, user1id, user2id)
    SELECT -- status: differential iff any of the joined input records were differential
           CASE WHEN 'D' IN (l1.status, l2.status, f.status) THEN 'D' ELSE 'I' END AS status
         , l1.commentid, f.user1id, f.user2id
      FROM likes l1, likes l2
         , friends f
     WHERE 1=1
       AND l1.userid = f.user1id
       AND f.user2id = l2.userid
       AND l1.commentid = l2.commentid
;
*/

-- allow index only scan to emulate index-organized table
CREATE INDEX comment_friends_comment_userids ON comment_friends (commentid, user1id, user2id);
CREATE INDEX comment_friends_comment_user1id ON comment_friends (commentid, user1id);

CREATE UNIQUE INDEX q2_comment_friends_closed_all ON q2_comment_friends_closed (commentid, head_userid, tail_userid);
