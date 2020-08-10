#!/bin/bash

ls solutions/Dockerfile-* | sed -r 's/.*Dockerfile-(.+$)/\1/'
