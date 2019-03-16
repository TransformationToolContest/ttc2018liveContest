#!/bin/bash

POSTFIX=-initial.csv
PREFIX=${NEO4J_DATA_DIR}/graph-

$NEO4J_HOME/bin/neo4j-import --into $NEO4J_DB_DIR \
  --id-type=INTEGER \
  --nodes:Message:Post "${PREFIX}posts${POSTFIX}" \
  --nodes:Message:Comment "${PREFIX}comments${POSTFIX}" \
  --nodes:User "${PREFIX}users${POSTFIX}" \
  --relationships:FRIEND "${PREFIX}friends${POSTFIX}" \
  --relationships:LIKES "${PREFIX}likes${POSTFIX}" \
  --relationships:COMMENT_TO "${PREFIX}comment-to${POSTFIX}" \
  --relationships:ROOT_POST "${PREFIX}root-post${POSTFIX}" \
  --relationships:SUBMITTER "${PREFIX}submitter${POSTFIX}" \
--delimiter '|'
