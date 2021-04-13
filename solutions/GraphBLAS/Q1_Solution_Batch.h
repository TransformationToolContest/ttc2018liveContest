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
        top_scores_vector.reserve(top_count);

        // convert row indices to original post IDs
        std::transform(top_scores.rbegin(), top_scores.rend(), std::back_inserter(top_scores_vector),
                       [&input](const auto &score_tuple) {
                           return input.posts[std::get<2>(score_tuple)].post_id;
                       });

        return top_scores_vector;
    }

    static GBxx_Object<GrB_Vector>
    get_score_vec(GrB_Index posts_size, GrB_Matrix root_post_tran_for_reduce, GrB_Matrix root_post_tran_for_mxv,
                  GrB_Vector likes_count_vec) {
        GBxx_Object<GrB_Vector> score_vec = GB(GrB_Vector_new, GrB_UINT64, posts_size);

        // count number of comments for each post
        ok(GrB_Matrix_reduce_BinaryOp(score_vec.get(), GrB_NULL, GrB_NULL,
                                      GrB_PLUS_UINT64, root_post_tran_for_reduce,
                                      GrB_NULL));

        GBxx_unary_function<uint64_t, uint64_t> multiplyBy10_func = [](auto result, auto input) {
            *result = *input * 10;
        };

        GBxx_Object<GrB_UnaryOp> multiplyBy10 = GB(GrB_UnaryOp_new,
                                                   reinterpret_cast<GxB_unary_function>(multiplyBy10_func),
                                                   GrB_UINT64, GrB_UINT64);

        // scale score by 10
        ok(GrB_Vector_apply(score_vec.get(), GrB_NULL, GrB_NULL, multiplyBy10.get(), score_vec.get(), GrB_NULL));

        // score = 10*comment_count + likes_count
        // increase score of each post by the number of likes
        // select likes number of each comment which belongs to the post (mul: times)
        // add up them per post (add: plus)
        ok(GrB_mxv(score_vec.get(), GrB_NULL, GrB_PLUS_UINT64,
                   GxB_PLUS_TIMES_UINT64,
                   root_post_tran_for_mxv, likes_count_vec,
                   GrB_NULL));
        return score_vec;
    }

    virtual GBxx_Object<GrB_Vector> get_score_vec() const {
        return get_score_vec(input.posts_size(),
                             input.root_post_tran_NEW.get(), input.root_post_tran_NEW.get(),
                             input.likes_count_vec.get());
    }

    virtual void save_score_vec(GBxx_Object<GrB_Vector> score_vec) {
        // noop
    }

public:
    using Q1_Solution::Q1_Solution;

    std::vector<score_type> calculate_score() {
        std::vector<score_type> top_scores;
        top_scores.reserve(top_count + 1);

        // compute score for each post
        GBxx_Object<GrB_Vector> score_vec = get_score_vec();

        GrB_Index scores_nvals;
        ok(GrB_Vector_nvals(&scores_nvals, score_vec.get()));

        std::vector<GrB_Index> score_vector_indices(scores_nvals);
        std::vector<uint64_t> score_vector_vals(scores_nvals);

        // extract scores vector
        ok(GrB_Vector_extractTuples_UINT64(score_vector_indices.data(), score_vector_vals.data(), &scores_nvals,
                                           score_vec.get()));

        save_score_vec(std::move(score_vec));

        for (GrB_Index i = 0; i < scores_nvals; ++i) {
            GrB_Index post_col = score_vector_indices[i];
            uint64_t score = score_vector_vals[i];

            add_score_to_toplist(top_scores, std::make_tuple(score, input.posts[post_col].timestamp, post_col));
        }

        sort_top_scores(top_scores);

        return top_scores;
    }

    std::vector<uint64_t> initial_calculation() override {
        return convert_score_type_to_post_id(calculate_score(), input);
    }

    std::vector<uint64_t> update_calculation(int iteration, const Q1_Input::Update_Type &current_updates) override {
        return convert_score_type_to_post_id(calculate_score(), input);
    }
};
