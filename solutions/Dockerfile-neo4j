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
      wget && \
    update-java-alternatives --set java-1.11.0-openjdk-amd64 && \
    rm -rf /var/lib/apt/lists/*

ENV NEO4J_NAME=neo4j-community-3.5.20
ENV NEO4J_HOME=/opt/$NEO4J_NAME
WORKDIR /opt
RUN wget -qO- "https://neo4j.com/artifact.php?name=${NEO4J_NAME}-unix.tar.gz" | tar -xzv

WORKDIR /ttc

# most frequently changing folders
COPY solutions/Neo4jSolution solutions/Neo4jSolution
COPY solutions/Neo4jSolutionBatch solutions/Neo4jSolutionBatch
COPY solutions/Neo4jSolutionBatch_algo_with_filtered_edges solutions/Neo4jSolutionBatch_algo_with_filtered_edges
COPY solutions/Neo4jSolutionBatch_rebuild_overlay solutions/Neo4jSolutionBatch_rebuild_overlay
COPY solutions/Neo4jSolution_explicit_component solutions/Neo4jSolution_explicit_component
COPY solutions/Neo4jSolution_explicit_component_algo solutions/Neo4jSolution_explicit_component_algo
COPY solutions/Neo4jSolution_overlay_graph solutions/Neo4jSolution_overlay_graph
COPY config/config-docker-neo4j.json config/config.json

# build solutions in config.json
RUN scripts/run.py -b

# run the benchmark in container
CMD scripts/run.py -mc
