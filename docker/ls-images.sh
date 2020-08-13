#!/bin/bash

ls docker/Dockerfile-* | sed -r 's/.*Dockerfile-(.+$)/\1/'
