#!/bin/bash

ddlog_solution_dir=$(dirname $(dirname $(realpath $0)))

# Download ddlog v0.39.0 from Github repository.
export DDLOG_HOME=$ddlog_solution_dir/ddlog
echo DDLOG_HOME is set to $DDLOG_HOME

chmod +x $DDLOG_HOME/bin/ddlog
echo The location of ddlog binary is $(which $DDLOG_HOME/bin/ddlog)

# For compiled ddlog binary in default path
# export DDLOG_HOME=$HOME/.local/bin/ddlog
# For ddlog compiled from source and change the path if the source code is in a different directory.
# export DDLOG_HOME=$HOME/differential-datalog

# Create runtime for socialnetwork domain
$DDLOG_HOME/bin/ddlog -i snq1.dl && 
# Open the folder and build the runtime in Rust
(cd snq1_ddlog && cargo build --release) && 
# Open the folder and build the project that depends on the runtime 
(cd snq1_lib && cargo build --release) 