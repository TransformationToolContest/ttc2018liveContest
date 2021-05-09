#!/bin/bash

REPO=https://gitlab.eclipse.org/eclipse/hawk/hawk.git
SHA=3d6945abdf043bb2779d8fc9fa4da6152dd11a29

if test ! -d hawk -o "$(cd hawk && git rev-parse --sq HEAD)" != "$SHA" ; then
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
