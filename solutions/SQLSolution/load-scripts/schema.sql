/*
drop table if exists posts cascade;
drop table if exists comments cascade;
drop table if exists users cascade;
drop table if exists friends cascade;
drop table if exists likes cascade;
-- */

-- All the tables are partitioned based on the record status:
--  * 'I' (initial or image snapshot) stands for the steady data, and
--  * 'D' stands for the difference.
-- The partition tables itself have the status as name postfix

create table posts (
  status char(1) not null
, id bigint not null
, ts timestamp without time zone not null
, content text
, submitterid bigint not null
) partition by list (status);

create table posts_i partition of posts for values in ('I');
 alter table posts_i alter status set default 'I';
create table posts_d partition of posts for values in ('D');
 alter table posts_d alter status set default 'D';

create table comments (
  like posts including all
, previousid bigint not null
, postid bigint not null
) partition by list (status);

create table comments_i partition of comments for values in ('I');
 alter table comments_i alter status set default 'I';
create table comments_d partition of comments for values in ('D');
 alter table comments_d alter status set default 'D';

create table users (
  status char(1) not null
, id bigint not null
, name varchar
) partition by list (status);

create table users_i partition of users for values in ('I');
 alter table users_i alter status set default 'I';
create table users_d partition of users for values in ('D');
 alter table users_d alter status set default 'D';

create table friends (
  status char(1) not null
, user1id bigint not null
, user2id bigint not null
) partition by list (status);

create table friends_i partition of friends for values in ('I');
 alter table friends_i alter status set default 'I';
create table friends_d partition of friends for values in ('D');
 alter table friends_d alter status set default 'D';

create table likes (
  status char(1) not null
, userid bigint not null
, commentid bigint not null
) partition by list (status);

create table likes_i partition of likes for values in ('I');
 alter table likes_i alter status set default 'I';
create table likes_d partition of likes for values in ('D');
 alter table likes_d alter status set default 'D';

-- hold friendships where both parties like that comment
--drop table if exists comment_friends cascade;
create table comment_friends (
  status char(1) not null
, commentid bigint not null
, user1id bigint not null
, user2id bigint not null
) partition by list (status);

create table comment_friends_i partition of comment_friends for values in ('I');
 alter table comment_friends_i alter status set default 'I';
create table comment_friends_d partition of comment_friends for values in ('D');
 alter table comment_friends_d alter status set default 'D';

-- maintain result for q1 here
create table q1_result (
  postid bigint not null
, postts timestamp without time zone not null
, score bigint not null
);

-- maintain comment_friends closed here
create table q2_comment_friends_closed (
  commentid bigint not null
, head_userid bigint not null
, tail_userid bigint not null
);
