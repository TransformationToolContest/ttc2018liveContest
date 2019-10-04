cmake_minimum_required(VERSION 3.14)
project(graphblas)

set(CMAKE_CXX_STANDARD 17)

link_libraries(graphblas lagraph)
add_library(
        graphblas_utils
        SHARED
        utils.cpp
)

link_libraries(graphblas_utils)
add_executable(ttc-q2 main.cpp)
