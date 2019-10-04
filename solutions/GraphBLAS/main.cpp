#include <algorithm>

extern "C" {
#include <GraphBLAS.h>
#include <LAGraph.h>
}

#include "utils.h"
#include "computation_timer.hpp"

GrB_Vector WeaklyConnectedComponents(GrB_Matrix A, bool directed) {
    ComputationTimer total_timer{"WeaklyConnectedComponents"};
    GrB_Info info;

    GrB_Index n;
    GrB_Matrix_nrows(&n, A);

    GrB_Matrix C = A;
    if (directed) {
        OK(GrB_Matrix_new(&C, GrB_BOOL, n, n))

        GrB_Descriptor desc;
        OK(GrB_Descriptor_new(&desc))
        OK(GrB_Descriptor_set(desc, GrB_INP1, GrB_TRAN))

        OK(GrB_eWiseAdd_Matrix_BinaryOp(C, GrB_NULL, GrB_NULL, GrB_LOR, A, A, desc))
        OK(GrB_Descriptor_free(&desc))
    } else {
        C = A;
    }
    GrB_Vector components = NULL;
    LAGraph_cc(C, &components);

    return components;
}

int main(int argc, char **argv) {
    BenchmarkParameters parameters = ParseBenchmarkParameters(argc, argv);

    LAGraph_init();
    GxB_Global_Option_set(GxB_GLOBAL_NTHREADS, parameters.thread_num);

    GrB_Index const NUM_NODES = 7;
    GrB_Index const NUM_EDGES = 12;
    GrB_Index row_indices[] = {0, 0, 1, 1, 2, 3, 3, 4, 5, 6, 6, 6};
    GrB_Index col_indices[] = {1, 3, 4, 6, 5, 0, 2, 5, 2, 2, 3, 4};
    bool values[] = {true, true, true, true, true, true, true, true, true, true, true, true};
    GrB_Matrix graph;
    GrB_Matrix_new(&graph, GrB_BOOL, NUM_NODES, NUM_NODES);
    GrB_Matrix_build_BOOL(graph,
                          row_indices, col_indices, values,
                          NUM_EDGES, GrB_LOR);

    GrB_Index NODE = 2;
    GrB_Vector result, vec;
    GrB_Vector_new(&result, GrB_BOOL, NUM_NODES);
    GrB_Vector_new(&vec, GrB_BOOL, NUM_NODES);
    GrB_Vector_setElement_BOOL(vec, true, NODE);

    WriteOutDebugMatrix("GRAPH", graph);
    WriteOutDebugVector("Target node", vec);

    std::cout << "Processing starts at: " << GetCurrentMilliseconds() << std::endl;
    GrB_mxv(result, GrB_NULL, GrB_NULL, GxB_LOR_LAND_BOOL, graph, vec, GrB_NULL);
    std::cout << "Processing ends at: " << GetCurrentMilliseconds() << std::endl;

    WriteOutDebugVector("Sources", result);

    // Cleanup
    GrB_Matrix_free(&graph);
    GrB_finalize();
}
