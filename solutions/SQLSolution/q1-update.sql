WITH diff_posts AS (
    select p.id, p.ts, 10*count(distinct c.id) + count(l.userid) as score
    from posts_d p
        left join comments c on (p.id = c.postid)
        left join likes l on (c.id = l.commentid)
    where 1=1
    group by p.id, p.ts
)
, diff_comments AS (
    select p.id, p.ts, 10*count(distinct c.id) + count(l.userid) as score
    from posts p
        inner join comments_d c on (p.id = c.postid)
        left join likes l on (c.id = l.commentid)
    where 1=1
    group by p.id, p.ts
)
, diff_likes AS (
    select p.id, p.ts, count(l.userid) as score
    from posts p
        inner join comments c on (p.id = c.postid)
        inner join likes_d l on (c.id = l.commentid)
    where 1=1
    group by p.id, p.ts
)
INSERT INTO q1_result AS r (postid, postts, score)
    select id, ts, sum(score) as score
    from (
        select * from diff_posts
        UNION ALL
        select * from diff_comments
        UNION ALL
        select * from diff_likes
        ) t
    group by id, ts
ON CONFLICT (postid) DO UPDATE SET score = EXCLUDED.score + r.score
;
