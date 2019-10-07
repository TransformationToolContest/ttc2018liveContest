cmake_minimum_required(VERSION 3.14)
project(graphblas)

set(CMAKE_CXX_STANDARD 17)

if(CMAKE_CXX_COMPILER_ID STREQUAL "GNU")
    if(CMAKE_CXX_COMPILER_VERSION VERSION_LESS "7.0.0")
        message(FATAL_ERROR "Insufficient gcc version")
    endif()
endif()

link_libraries(graphblas lagraph)

add_executable(ttc-q2
        main.cpp
        load.cpp
        utils.cpp)
