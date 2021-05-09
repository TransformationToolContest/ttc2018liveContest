#!/bin/bash
set -Eeuo pipefail

cd "$(dirname "$0")"

if ! sha256sum -c --status checksum.sha256 2>/dev/null; then
    rm -rf ./*/
    echo Downloading ddlog
    wget -qO- "https://github.com/vmware/differential-datalog/releases/download/v0.39.0/ddlog-v0.39.0-20210411172417-linux.tar.gz" \
        | tar -zxC .. ddlog/{bin,lib}/
    sha256sum -c --quiet checksum.sha256
    echo ddlog is downloaded and extracted
else
    echo ddlog is already downloaded
fi
