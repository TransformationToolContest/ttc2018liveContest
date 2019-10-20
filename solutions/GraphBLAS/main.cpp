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

    std::unique_ptr<Q2_Solution> solution;

    if (parameters.Tool == "GBq2-Batch")
        solution = std::make_unique<Q2_Solution_Batch>(parameters);
    else if (parameters.Tool == "GBq2-Incr-Comment")
        solution = std::make_unique<Q2_Solution_Incremental_Per_Comment>(parameters);
    else
        throw std::runtime_error{"Unknown tool: " + parameters.Tool};

    solution->load();
    solution->initial();

    for (int iteration = 1; iteration <= parameters.Sequences; ++iteration) {
        solution->update(iteration);
    }

    // Cleanup
    solution->free();
    ok(GrB_finalize());
}
