#pragma once

#include <queue>
#include <algorithm>
#include <cassert>
#include <numeric>
#include <memory>
#include <set>
#include "utils.h"
#include "load.h"
#include "Q1_Solution.h"

class Q1_Solution_Batch : public Q1_Solution {
protected:
    static std::vector<uint64_t>
    convert_score_type_to_post_id(const std::vector<score_type> &top_scores, const Q1_Input &input) {
        std::vector<uint64_t> top_scores_vector;
        top_scores_vector.reserve(3);

        std::transform(top_scores.begin(), top_scores.end(), std::back_inserter(top_scores_vector),
                       [&input](const auto &score_tuple) {
                           return input.posts[std::get<2>(score_tuple)].post_id;
                       });

        return top_scores_vector;
    }

public:
    using Q1_Solution::Q1_Solution;

    std::vector<score_type> calculate_score() {
        queue_type top_scores;

        GrB_Object_cpp<GrB_Vector> score_vec = GB(GrB_Vector_new, GrB_UINT64, input.posts_size());

        ok(GrB_Matrix_reduce_BinaryOp(score_vec.get(), GrB_NULL, GrB_NULL,
                                      GrB_PLUS_UINT64, input.root_post_tran.get(),
                                      GrB_NULL));

        GB_unary_function_cpp<uint64_t, uint64_t> multiplyBy10_func = [](auto result, auto input) {
            *result = *input * 10;
        };

        GrB_Object_cpp<GrB_UnaryOp> multiplyBy10 = GB(GrB_UnaryOp_new,
                                                      reinterpret_cast<GxB_unary_function>(multiplyBy10_func),
                                                      GrB_UINT64, GrB_UINT64);

        ok(GrB_Vector_apply(score_vec.get(), GrB_NULL, GrB_NULL, multiplyBy10.get(), score_vec.get(), GrB_NULL));

        ok(GrB_mxv(score_vec.get(), GrB_NULL, GrB_PLUS_UINT64,
                   GxB_PLUS_TIMES_UINT64,
                   input.root_post_tran.get(), input.likes_count_vec.get(),
                   GrB_NULL));

        GrB_Index *score_vector_indices_ptr = nullptr;
#ifndef NDEBUG
        std::vector<GrB_Index> score_vector_indices(input.posts_size());
        score_vector_indices_ptr = score_vector_indices.data();
#endif
        std::vector<uint64_t> score_vector(input.posts_size());

        // first consecutive part of score_vec contains posts with comments i.e. posts with non-zero score
        // remaining posts have no comments i.e. zero score
        GrB_Index scores_nvals = score_vector.size();
        ok(GrB_Vector_extractTuples_UINT64(score_vector_indices_ptr, score_vector.data(), &scores_nvals,
                                           score_vec.get()));

#ifndef NDEBUG
        // check first non-zero score part whether it is consecutive or not
        for (GrB_Index post_col = 0; post_col < scores_nvals; ++post_col) {
            assert(score_vector_indices[post_col] == post_col);
        }
#endif

        // scores_nvals is the length of non-zero part of scores_score_vector
        for (GrB_Index post_col = 0; post_col < scores_nvals; ++post_col) {
            uint64_t score = score_vector[post_col];

            // TODO: avoid timestamp lookup if possible
            top_scores.push(std::make_tuple(score, input.posts[post_col].timestamp, post_col));

            if (top_scores.size() > 3)
                top_scores.pop();
        }

        std::vector<score_type> top_scores_vector;

        while (!top_scores.empty()) {
            top_scores_vector.push_back(top_scores.top());
            top_scores.pop();
        }

        return top_scores_vector;
    }

    std::vector<uint64_t> initial_calculation() override {
        return convert_score_type_to_post_id(calculate_score(), input);
    }

    std::vector<uint64_t> update_calculation(int iteration, const Update_Type_Q1 &current_updates) override {
        return convert_score_type_to_post_id(calculate_score(), input);
    }
};
