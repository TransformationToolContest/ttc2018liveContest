#pragma once

#include <stdexcept>
#include <vector>
#include <chrono>
#include <memory>

#include "gb_utils.h"

struct BenchmarkParameters {
    std::string ChangePath;
    std::string RunIndex;
    int Sequences;
    std::string Tool;
    std::string ChangeSet;
    std::string Query;
    int ThreadsNum = 0;
};

BenchmarkParameters parse_benchmark_params();

using score_type = std::tuple<uint64_t, time_t, GrB_Index>;

struct BenchmarkPhase {
    static const std::string Initialization;
    static const std::string Load;
    static const std::string Initial;
    static const std::string Update;
};

void report(const BenchmarkParameters &parameters, int iteration, const std::string &phase,
            std::chrono::nanoseconds runtime,
            std::optional<std::vector<uint64_t>> result_reversed_opt = std::nullopt);
