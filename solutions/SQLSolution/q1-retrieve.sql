select postid, score
  from q1_result
 where 1=1
 order by score desc, postts desc limit 3
;
