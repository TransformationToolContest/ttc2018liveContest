# Before running Neo4j solutions

- https://neo4j.com/download-center/#community
  - https://neo4j.com/artifact.php?name=neo4j-community-3.5.13-unix.tar.gz
- `.env` file
  - Set `NEO4J_HOME=` to Neo4j installation
  - Increase Java maximum heap size if possible

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
