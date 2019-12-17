#include <string>
#include <chrono>
#include <optional>
#include <algorithm>
#include <iostream>

#include "utils.h"


std::string getenv_string(const char *name) {
    const char *value = std::getenv(name);
    if (value)
        return value;
    else
        throw std::runtime_error{std::string{"Missing environmental variable: "} + name};
}

BenchmarkParameters parse_benchmark_params() {
    BenchmarkParameters params;

    params.ChangePath = getenv_string("ChangePath");
    params.RunIndex = getenv_string("RunIndex");
    params.Sequences = std::stoi(getenv_string("Sequences"));
    params.Tool = getenv_string("Tool");
    params.ChangeSet = getenv_string("ChangeSet");
    params.Query = getenv_string("Query");

    std::transform(params.Query.begin(), params.Query.end(), params.Query.begin(),
                   [](char c) { return std::toupper(c); });

    return params;
}

const std::string BenchmarkPhase::Initialization = "Initialization";
const std::string BenchmarkPhase::Load = "Load";
const std::string BenchmarkPhase::Initial = "Initial";
const std::string BenchmarkPhase::Update = "Update";

void report_info(const BenchmarkParameters &parameters, int iteration, const std::string &phase) {
    std::cout
            << parameters.Tool << ';'
            << parameters.Query << ';'
            << parameters.ChangeSet << ';'
            << parameters.RunIndex << ';'
            << iteration << ';'
            << phase << ';';
}

void
report(const BenchmarkParameters &parameters, int iteration, const std::string &phase, std::chrono::nanoseconds runtime,
       std::optional<std::vector<uint64_t>> result_reversed_opt) {
    report_info(parameters, iteration, phase);
    std::cout << "Time" << ';' << runtime.count() << std::endl;

    if (result_reversed_opt) {
        const auto &result_reversed = result_reversed_opt.value();

        report_info(parameters, iteration, phase);
        std::cout << "Elements" << ';';

        for (auto iter = result_reversed.rbegin(); iter != result_reversed.rend(); ++iter) {
            auto comment_id = *iter;

            if (iter != result_reversed.rbegin())
                std::cout << '|';
            std::cout << comment_id;
        }
        std::cout << std::endl;
    }

}
