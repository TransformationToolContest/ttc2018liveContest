# Before running Neo4j solutions

- https://neo4j.com/download-center/#community
  - https://neo4j.com/artifact.php?name=neo4j-community-3.5.13-unix.tar.gz
- `.env` file
  - Set `NEO4J_HOME=` to Neo4j installation
  - Increase Java maximum heap size if possible

# Useful commands

## Get start options of running measurement
```console
tr '\0' '\n' < /proc/«PROC_ID»/environ | egrep "Debug|Sequences|Runs|RunIndex|Tool|ChangeSet|ChangePath|Query"
```
