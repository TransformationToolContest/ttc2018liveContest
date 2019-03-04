-- Populate posts table
COPY posts_i (id, ts, content, submitterid)
FROM 'PATHVAR/csv-posts-initial.csv' WITH DELIMITER '|' CSV;

-- Populate comments table
COPY comments_i (id, ts, content, submitterid, previousid, postid)
FROM 'PATHVAR/csv-comments-initial.csv' WITH DELIMITER '|' CSV;

-- Populate users table
COPY users_i (id, name)
FROM 'PATHVAR/csv-users-initial.csv' WITH DELIMITER '|' CSV;

-- Populate friends table
COPY friends_i (user1id, user2id)
FROM 'PATHVAR/csv-friends-initial.csv' WITH DELIMITER '|' CSV;

-- Populate likes table
COPY likes_i (userid, commentid)
FROM 'PATHVAR/csv-likes-initial.csv' WITH DELIMITER '|' CSV;
