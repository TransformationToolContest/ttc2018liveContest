# SQL solution

[PostgreSQL](https://www.postgresql.org/) solution for the TTC 2018 Live case.


## Generate CSV models

Use the modelConverter Gradle-task to generate the CSV models. Pass desired model size in the `modelSize` project property.

Models will be output as `models/$modelSize/csv-*-initial.csv`.

E.g. to generate CSV models for size 4, use:

```console
./gradlew modelConverter -PmodelSize=4
```

## Configuring the database

The default configuration uses the `ttc2018sf1` database.

On a typical Ubuntu install, you might want to run:

```bash
sudo -u postgres psql
```

To allow access from JDBC, you have to set a password. For example, to set the default password of user `postgres` to `foo`, issue the following command:

```sql
ALTER ROLE postgres PASSWORD 'foo';
```

If you want to create a separate user `usr` with the password `foo`, use the following commands:

```sql
CREATE USER usr PASSWORD 'foo';
ALTER ROLE usr WITH login createdb superuser;
```

## Allow access from all local users

Alternatively, you can allow any local users to access the database.

:warning: Warning: never do this on a production system.

Edit the `pg_hba.conf` file and add the following line:

```
local all postgres trust
# local all postgres peer
```

## Loading the data set

### Generating the data set

See above in Sec. *Generate CSV models*.

### Loading the data set

To run the load script, go the `load-scripts` directory, set the `PG_` environment variables (optional) and issue the following command:

```bash
export PG_DATA_DIR=
export PG_DB_NAME=
export PG_USER=
export PG_PORT=
./load.sh
```

The `load.sh` has default options that loads the dataset in the generator's directory to the `ttc2018sf1` database with your current user. If these fit your needs, just run the script as `./load.sh`.

If you get _Permission denied_ errors, change the permissions of your home directory to 755 - but please make sure you understand its implications first:

```bash
chmod 755 ~
```

To play around with the data, connect to PostgreSQL and switch to the database:

```sql
postgres=# \c ttc2018sf1
You are now connected to database "ttc2018sf1" as user "postgres".
ttc2018sf1=# SELECT count(*) FROM users;
# ...
```
