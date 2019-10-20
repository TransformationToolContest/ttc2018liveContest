#include <algorithm>
#include <memory>
#include <numeric>
#include <queue>
#include <fstream>
#include <iterator>
#include <iostream>
#include <chrono>
#include <set>

extern "C" {
#include <GraphBLAS.h>
#include <LAGraph.h>
}

#include "utils.h"
#include "Q2_Solution_Batch.h"
#include "Q2_Solution_Incremental_Per_Comment.h"

int main(int argc, char **argv) {
    BenchmarkParameters parameters = parse_benchmark_params();

    ok(LAGraph_init());
    GxB_Global_Option_set(GxB_GLOBAL_NTHREADS, parameters.thread_num);
    // std::cout << parameters.thread_num << '/' << omp_get_max_threads() << std::endl;

    Q2_Solution_Incremental_Per_Comment solution{parameters};

    solution.load();
    solution.initial();

    for (int iteration = 1; iteration <= parameters.Sequences; ++iteration) {
        solution.update(iteration);
    }

    // Cleanup
    solution.free();
    ok(GrB_finalize());
}
