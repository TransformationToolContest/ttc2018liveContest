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
        python3 r-base \
        rustc cargo \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /ttc
# most frequently changing folders
COPY solutions/differential/ solutions/differential/
COPY solutions/differential-08/ solutions/differential-08/
COPY config/config-docker-differential.json config/config.json

# build solutions in config.json
RUN scripts/run.py -b

# run the benchmark in container
CMD scripts/run.py -mc
