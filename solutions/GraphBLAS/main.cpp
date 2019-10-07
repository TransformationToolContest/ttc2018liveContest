#include <algorithm>

extern "C" {
#include <GraphBLAS.h>
#include <LAGraph.h>
}

#include "utils.h"
#include "computation_timer.hpp"
#include "load.h"

GrB_Vector WeaklyConnectedComponents(GrB_Matrix A, bool directed) {
    ComputationTimer total_timer{"WeaklyConnectedComponents"};

    GrB_Index n;
    ok(GrB_Matrix_nrows(&n, A));

    GrB_Matrix C = A;
    if (directed) {
        ok((GrB_Matrix_new(&C, GrB_BOOL, n, n)));

        GrB_Descriptor desc;
        ok((GrB_Descriptor_new(&desc)));
        ok((GrB_Descriptor_set(desc, GrB_INP1, GrB_TRAN)));

        ok((GrB_eWiseAdd_Matrix_BinaryOp(C, GrB_NULL, GrB_NULL, GrB_LOR, A, A, desc)));
        ok((GrB_Descriptor_free(&desc)));
    } else {
        C = A;
    }
    GrB_Vector components = nullptr;
    ok(LAGraph_cc(C, &components));

    return components;
}

int main(int argc, char **argv) {
    BenchmarkParameters parameters = ParseBenchmarkParameters(argc, argv);

    ok(LAGraph_init());
    GxB_Global_Option_set(GxB_GLOBAL_NTHREADS, parameters.thread_num);

    Q2_Input input = load();



    // Cleanup
    input.free();
    ok(GrB_finalize());
}
