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
CREATE INDEX friends_user2id ON friends (user2id);
CREATE INDEX likes_userid ON likes (userid);
CREATE INDEX likes_commentid ON likes (commentid);
