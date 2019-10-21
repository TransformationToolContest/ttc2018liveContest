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

std::unique_ptr<BaseSolution> init_solution(BenchmarkParameters &parameters) {
    if (parameters.Query == "Q2") {
        if (parameters.Tool == "GBq2-Batch")
            return std::make_unique<Q2_Solution_Batch>(parameters);
        if (parameters.Tool == "GBq2-Incr-Comment")
            return std::make_unique<Q2_Solution_Incremental_Per_Comment>(parameters);
    }

    throw std::runtime_error{"Unknown query and tool: " + parameters.Query + ", " + parameters.Tool};
}

int main(int argc, char **argv) {
    BenchmarkParameters parameters = parse_benchmark_params();

    ok(LAGraph_init());
    GxB_Global_Option_set(GxB_GLOBAL_NTHREADS, parameters.thread_num);
    // std::cout << parameters.thread_num << '/' << omp_get_max_threads() << std::endl;

    std::unique_ptr<BaseSolution> solution = init_solution(parameters);

    solution->load();
    solution->initial();

    for (int iteration = 1; iteration <= parameters.Sequences; ++iteration) {
        solution->update(iteration);
    }

    // Cleanup
    solution->free();
    ok(GrB_finalize());
}
