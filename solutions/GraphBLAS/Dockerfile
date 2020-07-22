ARG BASE_CONTAINER=ubuntu:20.04
FROM ${BASE_CONTAINER}

WORKDIR /ttc
COPY models models

# to prevent tzdata from requiring user input
# https://askubuntu.com/a/1013396/415610
ENV DEBIAN_FRONTEND=noninteractive

# TODO: merge installations when final
#RUN apt-get update && \
#    apt-get install -yq --no-install-recommends \
#        ... \
#    && rm -rf /var/lib/apt/lists/*
RUN apt-get update
RUN apt-get install -yq --no-install-recommends \
        ca-certificates apt-transport-https \
        git build-essential cmake \
        python3

WORKDIR /opt
ARG SS_RELEASE=v3.3.3
ARG SS_BURBLE=0
# TODO: set SS_COMPACT=0 for benchmark
ARG SS_COMPACT=1

# remove previous GraphBLAS install if exists
RUN rm -f /usr/local/lib/libgraphblas.so* && \
    git clone --depth 1 --branch ${SS_RELEASE} https://github.com/DrTimothyAldenDavis/GraphBLAS && \
    cd GraphBLAS/build && \
    cmake .. -DGB_BURBLE=${SS_BURBLE} -DGBCOMPACT=${SS_COMPACT} && \
    make -j$(nproc) install

RUN git clone --depth 1 https://github.com/GraphBLAS/LAGraph && \
    cd LAGraph/build && \
    cmake .. && \
    make -j$(nproc) install && \
    ldconfig

RUN apt-get install -yq --no-install-recommends \
        r-base
        # r-base-dev pandoc \
#        libssl-dev libxml2-dev libcurl4-openssl-dev

WORKDIR /ttc
# copy other folders
COPY expected-results expected-results
COPY output output
COPY reporting reporting
COPY scripts scripts
# most frequently changing folders
COPY solutions solutions
COPY config/config-docker-graphblas.json config/config.json

# build solutions in config.json
RUN scripts/run.py -b

# run the benchmark in container
CMD scripts/run.py -mc
