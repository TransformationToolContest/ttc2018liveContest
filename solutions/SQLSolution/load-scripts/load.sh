#!/bin/bash

MODEL_SIZE=${MODEL_SIZE:-1}

PG_DATA_DIR=${PG_DATA_DIR:-$(readlink -f $(dirname $0)/../../../models/$MODEL_SIZE)}
PG_DB_NAME=${PG_DB_NAME:-ttc2018sf1}
PG_USER=${PG_USER:-$USER}
PG_PORT=${PG_PORT:-5432}

echo $PG_DATA_DIR

/usr/bin/dropdb --if-exists $PG_DB_NAME -U $PG_USER -p $PG_PORT
/usr/bin/createdb $PG_DB_NAME -U $PG_USER -p $PG_PORT --template template0 -l "C"
/usr/bin/psql -d $PG_DB_NAME -U $PG_USER -p $PG_PORT -a -f schema.sql
(cat ttc2018-load.sql | sed "s|PATHVAR|$PG_DATA_DIR|g"; echo "\q\n") | /usr/bin/psql -d $PG_DB_NAME -U $PG_USER -p $PG_PORT
/usr/bin/psql -d $PG_DB_NAME -U $PG_USER -p $PG_PORT -a -f schema_constraints.sql
