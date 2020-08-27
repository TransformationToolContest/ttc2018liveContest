#!/bin/bash

GITHUB_REPO=TransformationToolContest/ttc2018liveContest
DOCKER_REPO=ftsrg/ttc2018

# exit immediately if a command fails
set -e

if [[ "$#" -eq 0 ]]; then
  echo Parameters:
  echo "  --pull"
  echo "  -b|--build"
  echo "  --build-if-not-fresh      # rebuild runnable image if it is based on different commit"
  echo "  -p|--push"
  echo "  -r|--run"
  echo
  echo "  -t|--tags \"TAG1 TAG2 ...\" # list the tags using \`docker/ls-images.sh\`"
  echo "  -h|--java-heap-size 6G    # run Java solutions with 6 GB of heap (default)"
  exit
fi

TAGS=$(docker/ls-images.sh)

DOCKER_BUILD_PARAMS=()
README_TXT_ARG=""

# log current version into the image
if git rev-parse --is-inside-work-tree >/dev/null 2>/dev/null; then
  GIT_SHA=$(git rev-parse HEAD)

  if ! git diff-index --quiet HEAD --; then
    README_TXT_ARG="${README_TXT_ARG}WARNING! There are uncommitted changes:"$'\n'
    README_TXT_ARG="${README_TXT_ARG}$(git diff --stat HEAD)"$'\n\n'
    >&2 echo "$README_TXT_ARG"
  fi
  README_TXT_ARG="${README_TXT_ARG}https://github.com/$GITHUB_REPO/commit/$GIT_SHA"
  DOCKER_BUILD_PARAMS+=("--build-arg" "README_TXT_ARG=$README_TXT_ARG")
else
  >&2 echo WARNING!!! This is not a git repo. I cannot log the current version into the image.
fi

DOCKER_PARAMS=()

# process parameters
# https://stackoverflow.com/a/33826763
while [[ "$#" -gt 0 ]]; do
    case $1 in
        --pull) pull=1 ;;
        -b|--build) build=1 ;;
        --build-if-not-fresh) build=1 ; build_if_not_fresh=1 ;;
        -p|--push) push=1 ;;
        -r|--run) run=1 ;;

        -t|--tags) TAGS="$2"; shift ;;
        -h|--java-heap-size) DOCKER_PARAMS+=("-e" "JAVA_HEAP_SIZE=$2"); shift ;;
        *) echo "Unknown parameter passed: $1"; exit 1 ;;
    esac
    shift
done

echo "Operations: ${build+build }${push+push }${run+run }"
echo Tags: $TAGS
echo

# where license permits push images to Docker Hub
PUBLISHABLE_TAGS=$(docker/ls-images.sh -t "$TAGS" -p)
TOOLS_TO_RUN=$(docker/ls-images.sh -t "$TAGS" -r)

if [[ $pull ]]; then
  echo ==================== Pull: $PUBLISHABLE_TAGS ====================
  for TAG in $PUBLISHABLE_TAGS; do
    echo "-------------------- Pull $TAG --------------------"
    docker pull $DOCKER_REPO:$TAG
  done
fi

if [[ $build ]]; then
  if [[ $build_if_not_fresh ]]; then
    TAGS_TO_BUILD=$TOOLS_TO_RUN
  else
    TAGS_TO_BUILD=$TAGS
  fi

  echo ==================== Build: $TAGS_TO_BUILD ====================
  for TAG in $TAGS_TO_BUILD; do
    echo "-------------------- Build $TAG --------------------"

    if [[ $build_if_not_fresh  ]]; then
      # allow command to fail
      set +e
      OLD_OR_MISSING_IMAGE=$(docker run --rm $DOCKER_REPO:$TAG cat README.md | grep -q $GIT_SHA || echo $?)
      set -e

      if [[ ! $OLD_OR_MISSING_IMAGE ]]; then
        echo Fresh image, build skipped
        continue
      fi
    fi

    DOCKER_CONTEXT=.
    if [[ $TAG == "common" ]]; then
      DOCKER_CONTEXT=models
    fi
    docker build "${DOCKER_BUILD_PARAMS[@]}" -t $DOCKER_REPO:$TAG -f "docker/Dockerfile-$TAG" $DOCKER_CONTEXT
  done
fi

if [[ $push ]]; then
  echo ==================== Push: $PUBLISHABLE_TAGS ====================
  for TAG in $PUBLISHABLE_TAGS; do
    echo "-------------------- Push $TAG --------------------"
    docker push $DOCKER_REPO:$TAG
  done
fi

if [[ $run ]]; then
  echo ==================== Run: $TOOLS_TO_RUN ====================
  for TOOL in $TOOLS_TO_RUN; do
    echo "-------------------- Run $TOOL --------------------"
    HOST_OUTPUT_PATH=$(realpath output/output-docker-$TOOL.csv)
    TOOL_DOCKER_CONFIG_PATH=$(realpath config/config-docker-$TOOL.json)
    touch "$HOST_OUTPUT_PATH"
    docker run --rm \
      -v "$HOST_OUTPUT_PATH":/ttc/output/output.csv \
      -v "$TOOL_DOCKER_CONFIG_PATH":/ttc/config/config.json \
      "${DOCKER_PARAMS[@]}" \
      -it \
      $DOCKER_REPO:$TOOL
  done
fi
