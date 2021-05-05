#!/bin/bash
# run the benchmark
/usr/bin/time -v bash -c 'eval scripts/run.py "${RUN_PARAMS}"'
