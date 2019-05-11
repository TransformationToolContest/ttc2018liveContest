# Before running Neo4j solutions

- Export path to Neo4j installation: `export NEO4J_HOME=`
- Increase Java maximum heap size if possible: `sed -i "s/-Xmx[^ ]*/-Xmx200G/" solutions/Neo*/solution.ini`
