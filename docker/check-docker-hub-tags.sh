#!/bin/bash

set -Eeuo pipefail

if [[ "$#" -le 1 ]]; then
  echo "Parameters: DOCKER_REPO TAG..."
  echo "  E.g.: library/ubuntu 18.04 20.04"
  exit
fi

DOCKER_REPO="$1"
shift

# https://stackoverflow.com/a/39641186
# https://stackoverflow.com/a/68654659
TOKEN=$(curl -s "https://auth.docker.io/token?service=registry.docker.io&scope=repository:$DOCKER_REPO:pull" | jq -r .token)
while [[ "$#" -gt 0 ]]; do
    TAG="$1"
    MANIFEST_URL="https://index.docker.io/v2/$DOCKER_REPO/manifests/$TAG"
    { curl --fail --silent --show-error --header "Authorization: Bearer $TOKEN" "$MANIFEST_URL" | jq "{name:.name, tag:.tag}" ; } \
        || { EXIT_CODE=$? ; echo "::error::Error accessing image: $MANIFEST_URL" ; exit $EXIT_CODE ; }
    shift
done
