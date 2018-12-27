/*
drop table if exists posts cascade;
drop table if exists comments cascade;
drop table if exists users cascade;
drop table if exists friends cascade;
drop table if exists likes cascade;
*/

create table posts (
  id bigint not null
, ts timestamp without time zone not null
, content text
, submitterid bigint not null
);

create table comments (
  like posts including all
, previousid bigint not null
, postid bigint not null
);

create table users (
  id bigint not null
, name varchar not null
);

create table friends (
  user1id bigint not null
, user2id bigint not null
);

create table likes (
  userid bigint not null
, commentid bigint not null
);

-- hold friendships where both parties like that comment
--drop table if exists comment_friends cascade;
create table comment_friends (
  commentid bigint not null
, user1id bigint not null
, user2id bigint not null
);
