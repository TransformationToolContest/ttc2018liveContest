#!/bin/bash
set -Eeuo pipefail

TARGET=$1

# Use downloaded ddlog v0.39.0 from Github repository.
export DDLOG_HOME=$(dirname $(realpath "$0"))/ddlog
echo DDLOG_HOME is set to $DDLOG_HOME
echo The location of ddlog binary is $(which $DDLOG_HOME/bin/ddlog)

# For compiled ddlog binary in default path
# export DDLOG_HOME=$HOME/.local/bin/ddlog
# For ddlog compiled from source and change the path if the source code is in a different directory.
# export DDLOG_HOME=$HOME/differential-datalog

cd "$TARGET"
# Create runtime for socialnetwork domain
"$DDLOG_HOME/bin/ddlog" -i "$TARGET.dl"

# https://stackoverflow.com/a/35412000
if declare -p DIFFERENTIAL_DATALOG_DISABLE_DEBUG &>/dev/null ; then
    echo -e "\n[profile.dev]\ndebug = false" | tee -a ${TARGET}_*/Cargo.toml
fi

# Open the folder and build the runtime in Rust
pushd "${TARGET}_ddlog"
cargo build --release
popd
# Open the folder and build the project that depends on the runtime 
pushd "${TARGET}_lib"
cargo build --release
popd
