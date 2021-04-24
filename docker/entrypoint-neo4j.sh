#!/bin/bash

# exit immediately if a command fails
set -e

# set Java heap size
ag 'Xm(s|x)[0-9]+G' -l0 | xargs -0 sed -i -r 's/Xm(s|x)[0-9]+G/Xm\1'$JAVA_HEAP_SIZE'/g'

# run the benchmark
/usr/bin/time -v scripts/run.py -mc
