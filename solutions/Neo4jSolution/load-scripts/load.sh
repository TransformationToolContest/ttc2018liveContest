#!/bin/bash

POSTFIX=-initial.csv
PREFIX=${NEO4J_DATA_DIR}/graph-

$NEO4J_HOME/bin/neo4j-admin import \
  --delimiter '|' \
  --id-type=INTEGER \
  --nodes=Submission:Post="${PREFIX}posts${POSTFIX}" \
  --nodes=Submission:Comment="${PREFIX}comments${POSTFIX}" \
  --nodes=User="${PREFIX}users${POSTFIX}" \
  --relationships=FRIEND="${PREFIX}friends${POSTFIX}" \
  --relationships=LIKES="${PREFIX}likes${POSTFIX}" \
  --relationships=COMMENT_TO="${PREFIX}comment-to${POSTFIX}" \
  --relationships=SUBMITTER="${PREFIX}submitter${POSTFIX}"
