#!/bin/bash

DOCKER_REPO=ftsrg/ttc2018

# exit immediately if a command fails
set -e

if [[ "$#" -eq 0 ]]; then
  echo Parameters:
  echo "  -b|--build"
  echo "  -p|--push"
  echo "  -r|--run"
  exit
fi

# https://stackoverflow.com/a/33826763
while [[ "$#" -gt 0 ]]; do
    case $1 in
        -b|--build) build=1 ;;
        -p|--push) push=1 ;;
        -r|--run) run=1 ;;
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

if [ $run ]; then
  for TOOL in $TOOLS; do
    echo "==================== Run $TOOL ===================="
    HOST_OUTPUT_PATH=$(realpath output/output-docker-$TOOL.csv)
    TOOL_DOCKER_CONFIG_PATH=$(realpath config/config-docker-$TOOL.json)
    touch "$HOST_OUTPUT_PATH"
    docker run --rm \
      -v "$HOST_OUTPUT_PATH":/ttc/output/output.csv \
      -v "$TOOL_DOCKER_CONFIG_PATH":/ttc/config/config.json \
      "$DOCKER_REPO:$TOOL"
  done
fi
