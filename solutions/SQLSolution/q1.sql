-- we assume that no user puts more than 1 like on a single comment
select p.id, 10*count(distinct c.id) + count(l.userid) as score
  from posts p
       left join comments c on (p.id = c.postid)
       left join likes l on (c.id = l.commentid)
 where 1=1
 group by p.id, p.ts
 order by score desc, p.ts desc limit 3
;
