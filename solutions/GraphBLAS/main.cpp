#include <algorithm>
#include <memory>
#include <numeric>
#include <queue>
#include <fstream>
#include <iterator>
#include <iostream>
#include <chrono>
#include <set>

#include "gb_utils.h"
#include "Q1_Solution_Batch.h"
#include "Q1_Solution_Incremental.h"
#include "Q2_Solution_Batch.h"
#include "Q2_Solution_Incremental_Per_Comment.h"

std::unique_ptr<BaseSolution> init_solution(BenchmarkParameters &parameters) {
    if (parameters.Query == "Q1") {
        if (parameters.Tool.rfind("GB-Batch", 0) == 0)
            return std::make_unique<Q1_Solution_Batch>(parameters);
        if (parameters.Tool.rfind("GB-Incr", 0) == 0)
            return std::make_unique<Q1_Solution_Incremental>(parameters);
    }
    if (parameters.Query == "Q2") {
        if (parameters.Tool.rfind("GB-Batch", 0) == 0)
            return std::make_unique<Q2_Solution_Batch>(parameters);
        if (parameters.Tool.rfind("GB-Incr", 0) == 0)
            return std::make_unique<Q2_Solution_Incremental_Per_Comment>(parameters);
    }

    throw std::runtime_error{"Unknown query and tool: " + parameters.Query + ", " + parameters.Tool};
}

int main(int argc, char **argv) {
    BenchmarkParameters parameters = parse_benchmark_params();

    LAGraph_Init(NULL);
    if (parameters.ThreadsNum > 0)
        ok(GxB_Global_Option_set(GxB_GLOBAL_NTHREADS, parameters.ThreadsNum));
    int nthreads_max = 0;
    ok(GxB_Global_Option_get(GxB_GLOBAL_NTHREADS, &nthreads_max));
    std::cerr << "Threads: " << nthreads_max << '/' << omp_get_max_threads() << std::endl;

    std::unique_ptr<BaseSolution> solution = init_solution(parameters);

    solution->load();
    solution->initial();

    for (int iteration = 1; iteration <= parameters.Sequences; ++iteration) {
        solution->update(iteration);
    }

    // Cleanup
    ok(GrB_finalize());
}
