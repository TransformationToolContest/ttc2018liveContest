# Before running Neo4j solutions

- Export path to Neo4j installation: `export NEO4J_HOME=`
- Increase Java maximum heap size in `.env` file if possible

# Useful commands

## Get start options of running measurement
```console
tr '\0' '\n' < /proc/«PROC_ID»/environ | egrep "Debug|Sequences|Runs|RunIndex|Tool|ChangeSet|ChangePath|Query"
```
