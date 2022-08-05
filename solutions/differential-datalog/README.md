
# Differential Datalog implementation

This implementation is based on a dialect of Datalog language [Differential-datalog](https://github.com/vmware/differential-datalog) with incremental computation.

## User's Guide

Assuming that you use Ubuntu, you need the following prerequisites:

1. Install Rust as listed on https://rustup.rs/:

```bash
curl --proto '=https' --tlsv1.2 -sSf https://sh.rustup.rs | sh
```

2. Install GCC:

```bash
sudo apt install gcc
```

Adjust the `config/config.json` to include `differential-datalog` and run the program with:

```bash
scripts/run.py
```
