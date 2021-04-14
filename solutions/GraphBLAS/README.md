# GraphBLAS solution

## Prerequisites

Install CMake (the package in Ubuntu 18.04 satisfies the minimum version requirements):

```bash
sudo apt install -y cmake
```

## Grab and compile dependencies

```bash
export JOBS=$(nproc)

git clone --branch v4.0.3 https://github.com/DrTimothyAldenDavis/GraphBLAS/
cd GraphBLAS
make && sudo make install && sudo ldconfig
cd ..

curl -L https://github.com/GraphBLAS/LAGraph/archive/99ad4114cd6b3116195e9599fb9709780e06fc9d.tar.gz | tar zxv
cd LAGraph-*
make && sudo make install && sudo ldconfig
cd ..
```

## Configurations

JSON snippet:

```json
"Tools": ["GBq1-Batch", "GBq1-Incr", "GBq2-Batch", "GBq2-Incr-Comment"],
```
