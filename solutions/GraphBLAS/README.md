# GraphBLAS solution

## Prerequisites

Install CMake (the package in Ubuntu 18.04 satisfies the minimum version requirements):

```bash
sudo apt install -y cmake
```

## Grab and compile dependencies

```bash
export JOBS=$(nproc)

git clone --branch v4.0.1 https://github.com/DrTimothyAldenDavis/GraphBLAS/
cd GraphBLAS
make && sudo make install && sudo ldconfig
cd ..

git clone --branch 4Jan2021 https://github.com/GraphBLAS/LAGraph
cd LAGraph
make && sudo make install && sudo ldconfig
cd ..
```

## Configurations

JSON snippet:

```json
"Tools": ["GBq1-Batch", "GBq1-Incr", "GBq2-Batch", "GBq2-Incr-Comment"],
```
