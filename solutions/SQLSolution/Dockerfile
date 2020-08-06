ARG BASE_CONTAINER=ubuntu:20.04
FROM ${BASE_CONTAINER}

# to prevent tzdata from requiring user input
# https://askubuntu.com/a/1013396/415610
ENV DEBIAN_FRONTEND=noninteractive

WORKDIR /ttc
COPY models models
COPY expected-results expected-results
COPY output output
COPY reporting reporting
COPY scripts scripts

RUN apt-get update && \
    apt-get install -yq --no-install-recommends \
      openjdk-11-jdk-headless python3 r-base \
      postgresql-12 && \
    update-java-alternatives --set java-1.11.0-openjdk-amd64 && \
    rm -rf /var/lib/apt/lists/*

RUN sed -i '1ilocal all ttcuser trust' /etc/postgresql/12/main/pg_hba.conf && \
    service postgresql restart && \
    pg_lsclusters && \
    runuser postgres -lc 'psql -c "select version();"' && \
    runuser postgres -lc "psql -c \"create user ttcuser password 'secret'; alter role ttcuser with login createdb superuser;\""

# most frequently changing folders
COPY solutions/SQLSolution solutions/SQLSolution
COPY solutions/SQLSolutionBatch solutions/SQLSolutionBatch
COPY config/config-docker-sql.json config/config.json

# build solutions in config.json
RUN scripts/run.py -b

CMD solutions/SQLSolution/docker-entrypoint.sh
