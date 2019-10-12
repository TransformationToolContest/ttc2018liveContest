#pragma once

#include <stdexcept>

extern "C" {
#include <GraphBLAS.h>
#include <LAGraph.h>
}

/*
 * ARGUMENT PARSING FUNCTIONS
 */

typedef struct {
  std::string vertex_file;
  std::string edge_file;
  std::string market_file;
  std::string mapping_file;
  bool weighted = false;
  bool directed = false;
} ConverterParameters;

ConverterParameters ParseConverterParameters(int argc, char **argv);

time_t GetCurrentMilliseconds();

void WriteOutDebugMatrix(const char *title, GrB_Matrix result);

void WriteOutDebugVector(const char *title, GrB_Vector result);

/*
 * GRAPHBLAS HELPER FUNCTION
 */

//------------------------------------------------------------------------------
// ok: call a GraphBLAS method and check the result
//------------------------------------------------------------------------------

// ok(GrB_Info) is a function that processes result of a GraphBLAS method and checks the status;
// if a failure occurs, it returns the error status to the caller.

void inline ok(GrB_Info info) {
    if (info != GrB_SUCCESS) {
        throw std::runtime_error{std::string{"GraphBLAS error: "} + GrB_error()};
    }
}
