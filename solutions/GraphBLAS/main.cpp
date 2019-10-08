#include <algorithm>
#include <memory>
#include <numeric>
#include <cassert>
#include <queue>

extern "C" {
#include <GraphBLAS.h>
#include <LAGraph.h>
}

#include "utils.h"
#include "computation_timer.hpp"
#include "load.h"

int main(int argc, char **argv) {
    BenchmarkParameters parameters = ParseBenchmarkParameters(argc, argv);

    ok(LAGraph_init());
    GxB_Global_Option_set(GxB_GLOBAL_NTHREADS, parameters.thread_num);

    Q2_Input input = load("../../models/2/");

    using score_type = std::tuple<uint64_t, time_t, GrB_Index>;
    std::priority_queue<score_type, std::vector<score_type>, std::greater<>> top_scores;

    // make sure tuples are in row-major order (SuiteSparse extension)
    GxB_Format_Value format;
    ok(GxB_Matrix_Option_get(input.likes_matrix_tran, GxB_FORMAT, &format));
    if (format != GxB_BY_ROW) {
        throw std::runtime_error{"Matrix is not CSR"};
    }

    std::unique_ptr<GrB_Index[]> likes_trg_comment_columns{new GrB_Index[input.likes_num]},
            likes_src_user_columns{new GrB_Index[input.likes_num]};
    GrB_Index *likes_comment_array_last = likes_trg_comment_columns.get() + input.likes_num;

    // nullptr to avoid extracting matrix values (SuiteSparse extension)
    GrB_Index nvals = input.likes_num;
    ok(GrB_Matrix_extractTuples_BOOL(likes_trg_comment_columns.get(), likes_src_user_columns.get(), nullptr, &nvals,
                                     input.likes_matrix_tran));
    assert(nvals == input.likes_num);

    // find tuple sequences of each comment in row-major array
    GrB_Index *likes_comment_first = likes_trg_comment_columns.get();
    GrB_Index *likes_user_first = likes_src_user_columns.get();
    for (GrB_Index comment_col = 0; comment_col < input.comments_size(); ++comment_col) {
        if (likes_comment_first == likes_comment_array_last // no more values in likes matrix
            || comment_col < *likes_comment_first) {
            // no likes for this comment
            continue;
        } else {
            GrB_Index *likes_comment_last = likes_comment_first;
            GrB_Index *likes_user_last = likes_user_first;
            GrB_Index likes_count = 0;
            for (; likes_comment_last != likes_comment_array_last && *likes_comment_last == comment_col;
                   ++likes_comment_last, ++likes_user_last, ++likes_count);

            GrB_Matrix friends_subgraph;
            ok(GrB_Matrix_new(&friends_subgraph, GrB_BOOL, likes_count, likes_count));
            ok(GrB_Matrix_extract(friends_subgraph, GrB_NULL, GrB_NULL,
                                  input.friends_matrix, likes_user_first, likes_count, likes_user_first, likes_count,
                                  GrB_NULL));

            WriteOutDebugMatrix(std::to_string(comment_col).c_str(), friends_subgraph);

            // assuming that all component_ids will be in [0, n)
            GrB_Vector components_vector = nullptr;
            ok(LAGraph_cc(friends_subgraph, &components_vector));

            WriteOutDebugVector("Components", components_vector);

            std::cout << std::endl;

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

            std::cout << score << std::endl << std::endl;

            // TODO: avoid timestamp lookup if possible
            top_scores.push(std::make_tuple(score, input.comments[comment_col].timestamp, comment_col));

            if (top_scores.size() > 3)
                top_scores.pop();

            likes_comment_first = likes_comment_last;
            likes_user_first = likes_user_last;

            ok(GrB_Matrix_free(&friends_subgraph));
            ok(GrB_Vector_free(&components_vector));
        }
    }

    std::vector<score_type> top_scores_vector;
    while (!top_scores.empty()) {
        auto[score, timestamp, comment_col] = top_scores.top();
        top_scores.pop();

        uint64_t comment_id = input.comments[comment_col].comment_id;
        top_scores_vector.emplace_back(score, timestamp, comment_id);
    }

    for (auto iter = top_scores_vector.rbegin(); iter != top_scores_vector.rend(); ++iter) {
        auto[score, timestamp, comment_id] = *iter;

        if (iter != top_scores_vector.rbegin())
            std::cout << '|';
        std::cout << comment_id;
    }

    // Cleanup
    input.free();
    ok(GrB_finalize());
}
