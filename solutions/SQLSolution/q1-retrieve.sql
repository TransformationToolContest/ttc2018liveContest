select postid, score
  from q1_scoring
 where 1=1
 order by score desc, postts desc limit 3
;
