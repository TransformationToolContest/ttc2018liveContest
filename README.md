# The TTC 2018 Social Media Case

[![Build Status](https://travis-ci.org/TransformationToolContest/ttc2018liveContest.svg?branch=master)](https://travis-ci.org/TransformationToolContest/ttc2018liveContest)
 
## Case description

The `docs/2018_TTC_Live.pdf` file contains the [case description](https://github.com/TransformationToolContest/ttc2018liveContest/raw/master/docs/2018_TTC_Live.pdf).

## Prerequisites

* 64-bit operating system
* Python 3.3 or higher
* R

## Setup

The graph models are stored in the `models` directory. For sizes larger than 512, the files are zipped so make sure you unzip the required sizes first. For example, run:

```bash
cd models
unzip 1024.zip
cd ..
```

## Solution Prerequisites

* AOF, ATL: Requires Java 8 for build (can run with Java 11).
* Hawk: Requires Java (both 8 and 11 have tested and work).
* JastAdd: Requires Java 8 for running (already built).
* Naiad: Requires .NET Framework 4.5.1 (only works on Windows).
* NMF: You need to install [.NET Core 3.1](https://docs.microsoft.com/en-us/dotnet/core/install/linux-package-manager-ubuntu-1804)
* SQL: Requires PostgreSQL 11 or later.
* YAMTL: Requires Java 11 for running (already built).
* Differential: Requires Rust and Cargo.

Add your prerequisites here!

## Using the framework

The `scripts` directory contains the `run.py` script.
At a first glance, invoke it without any arguments so that the solution will be built, benchmarked, running times visualized and the results compared to the reference solution's.
One might fine tune the script for the following purposes:
* `run.py -b` -- builds the projects
* `run.py -b -s` -- builds the projects without testing
* `run.py -m` -- run the benchmark without building
* `run.py -v` -- visualizes the results of the latest benchmark
* `run.py -c` -- check results by comparing them to the reference output. The benchmark shall already been executed using `-m`.
* `run.py -t` -- build the project and run tests (usually unit tests as defined for the given solution)
* `run.py -d` -- runs the process in debug mode, i.e. with the `Debug` environment variable set to `true`

The `config` directory contains the configuration for the scripts:
* `config.json` -- configuration for the model generation and the benchmark
  * *Note:* the timeout as set in the benchmark configuration (default: 6000 seconds) applies to the gross cumulative runtime of the tool for a given changeset and update sequences. This also includes e.g. Initialization time which is not required by the benchmark framework to be measured.
    Timeout is only applied to the solutions' run phase (see `-m` for `run.py`), so it is not applied to e.g. the build phase (see `-b` for `run.py`).
* `reporting.json` -- configuration for the visualization

### Setting the heap size

By default, Java tools run with a heap size - both minimum (`Xms`) and maximum (`Xmx`) - of 6 GB.
This can be set to `«HEAP»` GB with the following script (using [`ag`](https://geoff.greer.fm/ag/)).

```bash
ag 'Xm(s|x)[0-9]+G' -l0 | xargs -0 sed -i -r 's/Xm(s|x)[0-9]+G/Xm\1«HEAP»G/g'
```

### Running the benchmark

The script runs the benchmark for the given number of runs, for the specified tools and change sequences.

The benchmark results are stored in a CSV file. The header for the CSV file is stored in the `output/header.csv` file.

## Reporting and visualization

Make sure you read the `README.md` file in the `reporting` directory and install all the requirements for R.

## Implementing the benchmark for a new tool

To implement a tool, you need to create a new directory in the solutions directory and give it a suitable name.

## Running the benchmark with Docker

Instructions for running the benchmark starting with a fresh Ubuntu 20.04 VM.

Create a file system on `$DEVICE` and mount it to `MOUNT_POINT`.

```bash
sudo mkfs.ext4 $DEVICE
sudo mount $DEVICE $MOUNT_POINT
```

Install docker with `apt`:

```bash
sudo apt install docker.io
```

Change Docker's storage location by editing `/etc/docker/daemon.json`. This file :

```bash
sudo vim /etc/docker/daemon.json
```

Add the following content
```json
{
  "data-root": "SET_TO_SOMEWHERE_UNDER_MOUNT_POINT"
}
```

Restart Docker:
```bash
sudo snap restart docker
```

Build the Docker images as follows.

:warning: Do not unzip the `1024.zip` file.

```bash
./docker.sh -b
```

Once they are built, set the configuration in `TODO`:

Then, run them using:

```bash
./docker TODO
```

TODO: set num threads
