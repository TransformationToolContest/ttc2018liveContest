-- we assume that no user puts more than 1 like on a single comment
insert into q1_result (postid, postts, score)
select p.id, p.ts, 10*count(distinct c.id) + count(l.userid) as score
  from posts p
       left join comments c on (p.id = c.postid)
       left join likes l on (c.id = l.commentid)
 where 1=1
 group by p.id, p.ts
;
