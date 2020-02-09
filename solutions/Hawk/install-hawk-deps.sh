#!/bin/bash

if ! test -d hawk; then
  git clone -b v1.2.0-rc1 --depth 1 https://git.eclipse.org/r/hawk/hawk.git
fi
cd hawk
mvn -B --quiet -f pom-plain.xml install -DskipTests
