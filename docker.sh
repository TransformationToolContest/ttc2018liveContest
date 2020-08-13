#!/bin/bash

DOCKER_REPO=ftsrg/ttc2018

# exit immediately if a command fails
set -e

if [[ "$#" -eq 0 ]]; then
  echo Parameters:
  echo "  -b|--build"
  echo "  -p|--push"
  echo "  -r|--run"
  echo
  echo "  -t|--tags \"TAG1 TAG2 ...\""
  exit
fi

TAGS=$(docker/ls-images.sh)

# https://stackoverflow.com/a/33826763
while [[ "$#" -gt 0 ]]; do
    case $1 in
        -b|--build) build=1 ;;
        -p|--push) push=1 ;;
        -r|--run) run=1 ;;

        -t|--tags) TAGS="$2"; shift ;;
        *) echo "Unknown parameter passed: $1"; exit 1 ;;
    esac
    shift
done

echo "Operations: ${build+build }${push+push }${run+run }"
echo Tags: $TAGS
echo

# where license permits push images to Docker Hub
TAGS_TO_PUSH=$(docker/ls-images.sh -t "$TAGS" -p)
TOOLS_TO_RUN=$(docker/ls-images.sh -t "$TAGS" -r)

if [ $build ]; then
  echo ==================== Build: $TAGS ====================
  for TAG in $TAGS; do
    echo "-------------------- Build $TAG --------------------"

    DOCKER_CONTEXT=.
    if [[ $TAG == "common" ]]; then
      DOCKER_CONTEXT=models
    fi
    docker build -t "$DOCKER_REPO:$TAG" -f "docker/Dockerfile-$TAG" $DOCKER_CONTEXT
  done
fi

if [ $push ]; then
  echo ==================== Push: $TAGS_TO_PUSH ====================
  for TAG in $TAGS_TO_PUSH; do
    echo "-------------------- Push $TAG --------------------"
    docker push "$DOCKER_REPO:$TAG"
  done
fi

if [ $run ]; then
  echo ==================== Run: $TOOLS_TO_RUN ====================
  for TOOL in $TOOLS_TO_RUN; do
    echo "-------------------- Run $TOOL --------------------"
    HOST_OUTPUT_PATH=$(realpath output/output-docker-$TOOL.csv)
    TOOL_DOCKER_CONFIG_PATH=$(realpath config/config-docker-$TOOL.json)
    touch "$HOST_OUTPUT_PATH"
    docker run --rm \
      -v "$HOST_OUTPUT_PATH":/ttc/output/output.csv \
      -v "$TOOL_DOCKER_CONFIG_PATH":/ttc/config/config.json \
      "$DOCKER_REPO:$TOOL"
  done
fi
