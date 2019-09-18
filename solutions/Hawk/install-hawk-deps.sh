#!/bin/bash

if ! test -d hawk; then
  git clone --branch v1.2.0-rc5 --depth 1 https://github.com/mondo-project/mondo-hawk.git hawk
fi
cd hawk
mvn -B --quiet -f pom-plain.xml install -DskipTests
