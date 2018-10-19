# The TTC 2018 Social Media Case

## Case description

The `docs/2018_TTC_Live.pdf` file contains the [case description](https://github.com/TransformationToolContest/ttc2018liveContest/raw/master/docs/2018_TTC_Live.pdf).

## Prerequisites

* 64-bit operating system
* Python 2.7 or higher
* R

## Solution Prerequisites

* NMF: You need to install [.NET Core 2.0](https://www.microsoft.com/net/download/linux-package-manager/ubuntu16-04/sdk-current)

Add your prerequisites here!

## Using the framework

The `scripts` directory contains the `run.py` script which is used for the following purposes:
* `run.py -b` -- builds the projects
* `run.py -b -s` -- builds the projects without testing
* `run.py -g` -- generates the instance models
* `run.py -m` -- runs the benchmark
* `run.py -v` -- visualizes the results of the latest benchmark
* `run.py -m -e` -- extract results
* `run.py -m -t` -- run tests


The `config` directory contains the configuration for the scripts:
* `config.json` -- configuration for the model generation and the benchmark
* `reporting.json` -- configuration for the visualization

### Running the benchmark

The script runs the benchmark for the given number of runs, for the specified tools and change sequences.

The benchmark results are stored in a CSV file. The header for the CSV file is stored in the `output/header.csv` file.

## Reporting and visualization

Make sure you read the `README.md` file in the `reporting` directory and install all the requirements for R.

## Implementing the benchmark for a new tool

To implement a tool, you need to create a new directory in the solutions directory and give it a suitable name.
