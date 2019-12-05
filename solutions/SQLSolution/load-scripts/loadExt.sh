#!/bin/bash
export MODEL_SIZE=1
export PG_DATA_DIR=$(readlink -f $(dirname "$0")/../../../models/)
export PG_DB_NAME=ttc2018sf1
export PG_USER=ab373
export PG_PORT=5432
./load.sh
