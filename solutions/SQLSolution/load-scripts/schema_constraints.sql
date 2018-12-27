ALTER TABLE posts ADD PRIMARY KEY (id);
ALTER TABLE comments ADD PRIMARY KEY (id);
ALTER TABLE users ADD PRIMARY KEY (id);

-- re-order friends on the storage level by user1id.
-- This order is not hold after writes to the table.
CREATE INDEX friends_user1id ON friends (user1id);
CLUSTER friends USING friends_user1id;

vacuum analyze;

-- additional indexes
CREATE UNIQUE INDEX friends_user1id_user2id ON friends (user1id, user2id);
CREATE UNIQUE INDEX friends_user2id_user1id ON friends (user2id, user1id);

-- create index on foreign keys
CREATE INDEX posts_submitterid ON posts (submitterid);
CREATE INDEX comments_submitterid ON comments (submitterid);
CREATE INDEX comments_previousid ON comments (previousid);
CREATE INDEX comments_postid ON comments (postid);
CREATE INDEX friends_user2id ON friends (user2id);
CREATE INDEX likes_userid ON likes (userid);
CREATE INDEX likes_commentid ON likes (commentid);

-- populate comment_friends table using a temporary table
-- to make use of the symmetric nature of friendships, i.e. calculate the join only once
BEGIN;

CREATE TEMPORARY TABLE tmp_comment_friends (
  LIKE comment_friends INCLUDING ALL
) ON COMMIT DROP;

INSERT INTO tmp_comment_friends
    SELECT l1.commentid, f.user1id, f.user2id
      FROM likes l1, likes l2
         , friends f
     WHERE 1=1
       AND l1.userid = f.user1id
       AND f.user2id = l2.userid
       AND l1.commentid = l2.commentid
;

INSERT INTO comment_friends (commentid, user1id, user2id)
  SELECT commentid, user1id, user2id
    FROM tmp_comment_friends
UNION ALL
  SELECT commentid, user2id, user1id
    FROM tmp_comment_friends
;

COMMIT;

-- allow index only scan to emulate index.organizes table
CREATE INDEX comment_friends_comment_userids ON comment_friends (commentid, user1id, user2id);
