# Before running Neo4j solutions

- Grab Neo4j Community Edition (https://neo4j.com/download-center/#community), e.g. on Linux, run:
  - `wget https://neo4j.com/artifact.php?name=neo4j-community-4.2.4-unix.tar.gz`
- Set the environment variables:
  - Set `export NEO4J_HOME=` to Neo4j installation directory
  - Increase Java maximum heap size as described in the main README

# Useful commands

## Find running measurements
```console
ps aux | grep 'bin/neo4j-solution' | cut -c 1-110
```

## Get start options of running measurement
```console
tr '\0' '\n' < /proc/«PROC_ID»/environ | egrep "Debug|Sequences|Runs|RunIndex|Tool|ChangeSet|ChangePath|Query"
```

## Concatenate more results
```console
cat output/output1.csv <(tail -qn +2 output/output2.csv output/output3.csv) > output/output.csv
```

## Print model sizes

Run from `models/` directory:
```console
echo size vertex edge; ls -d */ | grep -v example | grep -oE '[0-9]+' | sort -n | xargs -I {} bash -c 'echo {} $(($(cat {}/graph-comments-initial.csv {}/graph-posts-initial.csv {}/graph-users-initial.csv | wc -l) - 3)) $(($(cat {}/graph-comment-to-initial.csv {}/graph-root-post-initial.csv {}/graph-likes-initial.csv {}/graph-friends-initial.csv {}/graph-submitter-initial.csv | wc -l) - 5))'
```
