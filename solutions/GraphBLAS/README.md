# GraphBLAS solution

## Prerequisites

Install CMake (the package in Ubuntu 18.04 satisfies the minimum version requirements):

```bash
sudo apt install cmake
```

## Grab and compile dependencies

```bash
export JOBS=$(nproc)

wget http://faculty.cse.tamu.edu/davis/GraphBLAS/GraphBLAS-3.1.1.tar.gz
tar xf GraphBLAS-3.1.1.tar.gz
cd GraphBLAS-3.1.1
make && sudo make install && sudo ldconfig

git clone https://github.com/GraphBLAS/LAGraph
cd LAGraph
make && sudo make install && sudo ldconfig
```
