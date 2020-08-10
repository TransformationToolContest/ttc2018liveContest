#!/bin/bash

DOCKER_REPO=ftsrg/ttc2018

# exit immediately if a command fails
set -e

if [[ "$#" -eq 0 ]]; then
  build=1

  echo Parameters:
  echo "  -b|--build (default if none given)"
  echo "  -p|--push"
  echo
fi

# https://stackoverflow.com/a/33826763
while [[ "$#" -gt 0 ]]; do
    case $1 in
        -b|--build) build=1 ;;
        -p|--push) push=1 ;;
        *) echo "Unknown parameter passed: $1"; exit 1 ;;
    esac
    shift
done

TOOLS=$(./ls-docker-images.sh)
# where license permits push images to Docker Hub
TOOLS_TO_PUSH=$(echo "$TOOLS" | grep -v neo4j)

if [ $build ]; then
  for TOOL in $TOOLS; do
    echo "==================== Build $TOOL ===================="
    docker build -t "$DOCKER_REPO:$TOOL" -f "solutions/Dockerfile-$TOOL" .
  done
fi

if [ $push ]; then
  for TOOL in $TOOLS_TO_PUSH; do
    echo "==================== Push $TOOL ===================="
    docker push "$DOCKER_REPO:$TOOL"
  done
fi
