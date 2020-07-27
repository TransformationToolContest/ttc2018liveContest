#!/bin/bash

DOCKER_REPO=ftsrg/ttc2018

# exit immediately if a command fails
set -e

# Java 8 solutions
sudo docker build -t "$DOCKER_REPO:java8" -f solutions/Dockerfile-java8 .
sudo docker push "$DOCKER_REPO:java8"
