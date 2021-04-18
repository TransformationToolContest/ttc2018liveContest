#!/bin/bash

REPO=https://gitlab.eclipse.org/eclipse/hawk/hawk.git
SHA=575d2aaad351a0dffb3de41f60db62662b92d14c

if ! test -d hawk || ! test "$(cd hawk && git rev-parse --sq HEAD)" != "$SHA" ; then
  rm -rf hawk
  git clone "$REPO" hawk
  pushd hawk
  git reset --hard "$SHA"
  popd
fi

cd hawk
mvn -f pom-plain.xml install -Dmaven.javadoc.skip=true
cd neo4j2/org.eclipse.hawk.neo4j-v2
mvn -f pom-plain.xml install -Dmaven.javadoc.skip=true
