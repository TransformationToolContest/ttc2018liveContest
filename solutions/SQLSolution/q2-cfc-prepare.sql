-- for comment_friends_closed:
-- start with the users that liked a specific comment.
-- They are the vertices of the projected users graph for a comment
INSERT INTO q2_comment_friends_closed(commentid, head_userid, tail_userid)
SELECT l.commentid, l.userid AS head_userid, l.userid AS tail_userid
  FROM likes l
;
