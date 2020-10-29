#!/bin/bash
# use this command to copy settings (except Tools) from config/config.json to config/config-docker-*.json files

# exit immediately if a command fails
set -e

CONFIG_TEMPLATE=config/config.json

for DOCKER_TAG in $(docker/ls-images.sh -r); do
  CONFIG_FOR_DOCKER=config/config-docker-$DOCKER_TAG.json

  ORIGINAL_TOOLS_SETTING="$(grep -Ezo '"Tools"[^]]*]' "$CONFIG_FOR_DOCKER" | tr -d '\0' | tr '\n' '\f' )"

  # https://unix.stackexchange.com/a/525524
  # https://unix.stackexchange.com/a/152389
  sed -zr 's/"Tools"[^]]*]/'"$ORIGINAL_TOOLS_SETTING"/ "$CONFIG_TEMPLATE" | tr "\f" "\n" > "$CONFIG_FOR_DOCKER".tmp
  mv "$CONFIG_FOR_DOCKER".tmp "$CONFIG_FOR_DOCKER"
done
