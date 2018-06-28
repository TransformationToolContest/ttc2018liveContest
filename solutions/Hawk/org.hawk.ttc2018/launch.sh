#!/bin/bash

if [ "$#" != 3 ]; then
  echo "Usage: $0 batch|incU|incUQ 1|2|4|...|1024 Q1|Q2"
  exit 1
fi

PROFILE="$1"
CHANGE_SET="$2"
QUERY="$3"

export ChangePath=$(readlink -f "../../../models/$CHANGE_SET")
export ChangeSet="$CHANGE_SET"
export Query="$QUERY"
export RunIndex=0
export Sequences=20

mvn -quiet -P"$PROFILE" compile exec:exec
