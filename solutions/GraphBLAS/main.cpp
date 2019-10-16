#include <algorithm>
#include <memory>
#include <numeric>
#include <cassert>
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
#include "load.h"

namespace {

    using score_type = std::tuple<uint64_t, time_t, GrB_Index>;
    using queue_type = std::priority_queue<score_type, std::vector<score_type>, std::greater<>>;

    inline void
    compute_score_for_comment(const Q2_Input &input, GrB_Index comment_col, GrB_Index *likes_comment_array_first,
                              GrB_Index *likes_comment_array_last, GrB_Index *likes_user_array_first,
                              queue_type &top_scores) __attribute__ ((always_inline));

    void compute_score_for_comment(const Q2_Input &input, GrB_Index comment_col, GrB_Index *likes_comment_array_first,
                                   GrB_Index *likes_comment_array_last, GrB_Index *likes_user_array_first,
                                   queue_type &top_scores) {
        auto[likes_comment_first, likes_comment_last] = std::equal_range(likes_comment_array_first,
                                                                         likes_comment_array_last, comment_col);
        if (likes_comment_first != likes_comment_last) {
            GrB_Index likes_count = std::distance(likes_comment_first, likes_comment_last);
            GrB_Index *likes_user_first =
                    likes_user_array_first + std::distance(likes_comment_array_first, likes_comment_first);

            GrB_Matrix friends_subgraph;
            ok(GrB_Matrix_new(&friends_subgraph, GrB_BOOL, likes_count, likes_count));
            ok(GrB_Matrix_extract(friends_subgraph, GrB_NULL, GrB_NULL,
                                  input.friends_matrix, likes_user_first, likes_count, likes_user_first, likes_count,
                                  GrB_NULL));

            // assuming that all component_ids will be in [0, n)
            GrB_Vector components_vector = nullptr;
            ok(LAGraph_cc(friends_subgraph, &components_vector));

            GrB_Index nvals = input.likes_num;
            ok(GrB_Vector_nvals(&nvals, components_vector));
            assert(nvals == likes_count);

            GrB_Index n;
            ok(GrB_Vector_size(&n, components_vector));
            assert(n == likes_count);

            std::vector<uint64_t> components(likes_count),
                    component_sizes(likes_count);

            // nullptr: SuiteSparse extension
            nvals = likes_count;
            ok(GrB_Vector_extractTuples_UINT64(nullptr, components.data(), &nvals, components_vector));
            assert(nvals == likes_count);

            for (auto component_id:components)
                ++component_sizes[component_id];

            std::transform(component_sizes.begin(), component_sizes.end(), component_sizes.begin(),
                           [](uint64_t n) { return n * n; });

            uint64_t score = std::accumulate(component_sizes.begin(), component_sizes.end(), uint64_t());

            // TODO: avoid timestamp lookup if possible
            top_scores.push(std::make_tuple(score, input.comments[comment_col].timestamp, comment_col));

            if (top_scores.size() > 3)
                top_scores.pop();

            ok(GrB_Matrix_free(&friends_subgraph));
            ok(GrB_Vector_free(&components_vector));
        }
    }
}

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

struct BenchmarkPhase {
    static const std::string Initialization;
    static const std::string Load;
    static const std::string Initial;
    static const std::string Update;
};

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

void report(const BenchmarkParameters &parameters, int iteration, const std::string &phase,
            std::chrono::nanoseconds runtime,
            std::optional<std::vector<score_type>> result_reversed_opt = std::nullopt) {
    report_info(parameters, iteration, phase);
    std::cout << "Time" << ';' << runtime.count() << std::endl;

    if (result_reversed_opt) {
        const auto &result_reversed = result_reversed_opt.value();

        report_info(parameters, iteration, phase);
        std::cout << "Elements" << ';';

        for (auto iter = result_reversed.rbegin(); iter != result_reversed.rend(); ++iter) {
            auto comment_id = std::get<2>(*iter);

            if (iter != result_reversed.rbegin())
                std::cout << '|';
            std::cout << comment_id;
        }
        std::cout << std::endl;
    }

}

Q2_Input load(const BenchmarkParameters &parameters) {
    using namespace std::chrono;
    auto load_start = high_resolution_clock::now();

    Q2_Input input = load_initial(parameters);

    report(parameters, 0, BenchmarkPhase::Load, round<nanoseconds>(
            high_resolution_clock::now() - load_start));
    return input;
}

void initial(const BenchmarkParameters &parameters, const Q2_Input &input) {
    using namespace std::chrono;
    auto initial_start = high_resolution_clock::now();

    queue_type top_scores;

    std::unique_ptr<GrB_Index[]> likes_trg_comment_columns{new GrB_Index[input.likes_num]},
            likes_src_user_columns{new GrB_Index[input.likes_num]};
    GrB_Index *likes_comment_array_first = likes_trg_comment_columns.get(),
            *likes_comment_array_last = likes_trg_comment_columns.get() + input.likes_num,
            *likes_user_array_first = likes_src_user_columns.get();

    // nullptr to avoid extracting matrix values (SuiteSparse extension)
    GrB_Index nvals = input.likes_num;
    ok(GrB_Matrix_extractTuples_BOOL(likes_trg_comment_columns.get(), likes_src_user_columns.get(), nullptr, &nvals,
                                     input.likes_matrix_tran));
    assert(nvals == input.likes_num);

    // find tuple sequences of each comment in row-major array
    for (GrB_Index comment_col = 0; comment_col < input.comments_size(); ++comment_col) {
        compute_score_for_comment(input, comment_col, likes_comment_array_first, likes_comment_array_last,
                                  likes_user_array_first, top_scores);
    }

    // if comments with likes are not enough
    if (top_scores.size() < 3) {
        std::set<GrB_Index> comment_cols;
        std::vector<score_type> top_scores_vector;
        while (!top_scores.empty()) {
            auto[score, timestamp, comment_col] = top_scores.top();
            comment_cols.insert(comment_col);
            top_scores_vector.emplace_back(score, timestamp, comment_col);
            top_scores.pop();
        }
        for (const auto &score_tuple : top_scores_vector) {
            top_scores.push(score_tuple);
        }

        for (GrB_Index comment_col = 0; comment_col < input.comments_size(); ++comment_col) {
            if (comment_cols.emplace(comment_col).second) {
                top_scores.push(std::make_tuple(0, input.comments[comment_col].timestamp, comment_col));
            }

            if (top_scores.size() > 3)
                top_scores.pop();
        }
    }

    std::vector<score_type> top_scores_vector;
    while (!top_scores.empty()) {
        auto[score, timestamp, comment_col] = top_scores.top();
        top_scores.pop();

        uint64_t comment_id = input.comments[comment_col].comment_id;
        top_scores_vector.emplace_back(score, timestamp, comment_id);
    }

    report(parameters, 0, BenchmarkPhase::Initial, round<nanoseconds>(high_resolution_clock::now() - initial_start),
           top_scores_vector);
}

int main(int argc, char **argv) {
    BenchmarkParameters parameters = parse_benchmark_params();

    ok(LAGraph_init());
    GxB_Global_Option_set(GxB_GLOBAL_NTHREADS, parameters.thread_num);
    // std::cout << parameters.thread_num << '/' << omp_get_max_threads() << std::endl;

    Q2_Input input = load(parameters);
    initial(parameters, input);

    std::vector<std::string> types;
    for (int iteration = 1; iteration <= parameters.Sequences; ++iteration) {
        load_updates(types, iteration, parameters, input);
    }

    // Cleanup
    input.free();
    ok(GrB_finalize());
}
