#!/bin/bash

REPO=git://git.eclipse.org/gitroot/hawk/hawk.git
SHA1=32975427e4

if ! test -d hawk; then
  git clone "$REPO" hawk
  pushd hawk
  git reset --hard "$SHA1"
  popd
fi

cd hawk
mvn -f pom-plain.xml install -Dmaven.javadoc.skip=true
cd neo4j2/org.eclipse.hawk.neo4j-v2
mvn -f pom-plain.xml install -Dmaven.javadoc.skip=true
