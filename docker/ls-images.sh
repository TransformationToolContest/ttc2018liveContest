#!/bin/bash

# exit immediately if a command fails
set -e

# https://stackoverflow.com/a/49627999
# "catch/suppress exit status 1 (no lines were selected)" grep wrapper
c1grep() { grep "$@" || test $? = 1; }

TAGS=$(ls docker/Dockerfile-* | sed -r 's/.*Dockerfile-(.+$)/\1/')

# https://stackoverflow.com/a/33826763
while [[ "$#" -gt 0 ]]; do
    case $1 in
        -p|--publishable) publishable=1 ;;
        -r|--runnable) runnable=1 ;;

        # https://unix.stackexchange.com/a/105655
        -t|--tags) TAGS="${2// /$'\n'}"; shift ;;
        *) echo "Unknown parameter passed: $1"; exit 1 ;;
    esac
    shift
done

if [ $publishable ]; then
  TAGS=$(echo "$TAGS" | c1grep -v neo4j)
fi

if [ $runnable ]; then
  TAGS=$(echo "$TAGS" | c1grep -v common)
fi

echo "$TAGS"
