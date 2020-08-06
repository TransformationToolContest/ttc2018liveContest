#!/bin/bash

# exit immediately if a command fails
set -e

# start database, show status
service postgresql restart
pg_lsclusters

# run the benchmark
scripts/run.py -mc
