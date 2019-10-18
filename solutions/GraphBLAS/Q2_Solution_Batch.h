#pragma once

#include <queue>
#include <algorithm>
#include <cassert>
#include <numeric>
#include <memory>
#include <set>
#include "utils.h"
#include "load.h"
#include "Q2_Solution.h"

class Q2_Solution_Batch : public Q2_Solution {
protected:
    using queue_type = std::priority_queue<score_type, std::vector<score_type>, std::greater<>>;

    static std::vector<uint64_t> convert_score_type_to_comment_id(queue_type top_scores, const Q2_Input &input) {
        std::vector<uint64_t> top_scores_vector;
        top_scores_vector.reserve(3);

        while (!top_scores.empty()) {
            auto[score, timestamp, comment_col] = top_scores.top();
            top_scores.pop();

            uint64_t comment_id = input.comments[comment_col].comment_id;
            top_scores_vector.emplace_back(comment_id);
        }

        return top_scores_vector;
    }

    static inline void
    compute_score_for_comment(const Q2_Input &input, GrB_Index comment_col, const GrB_Index *likes_comment_array_first,
                              const GrB_Index *likes_comment_array_last, const GrB_Index *likes_user_array_first,
                              queue_type &top_scores) __attribute__ ((always_inline)) {
        auto[likes_comment_first, likes_comment_last] = std::equal_range(likes_comment_array_first,
                                                                         likes_comment_array_last, comment_col);
        if (likes_comment_first != likes_comment_last) {
            GrB_Index likes_count = std::distance(likes_comment_first, likes_comment_last);
            const GrB_Index *likes_user_first =
                    likes_user_array_first + std::distance(likes_comment_array_first, likes_comment_first);

            GrB_Matrix friends_subgraph;
            ok(GrB_Matrix_new(&friends_subgraph, GrB_BOOL, likes_count, likes_count));
            ok(GrB_Matrix_extract(friends_subgraph, GrB_NULL, GrB_NULL,
                                  input.friends_matrix, likes_user_first, likes_count, likes_user_first, likes_count,
                                  GrB_NULL));

            // assuming that all component_ids will be in [0, n)
            GrB_Vector components_vector = nullptr;
            ok(LAGraph_cc(friends_subgraph, &components_vector));

            GrB_Index nvals;
#ifndef NDEBUG
            nvals = input.likes_num;
            ok(GrB_Vector_nvals(&nvals, components_vector));
            assert(nvals == likes_count);

            GrB_Index n;
            ok(GrB_Vector_size(&n, components_vector));
            assert(n == likes_count);
#endif

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

public:

    using Q2_Solution::Q2_Solution;

    virtual void compute_score_for_all_comments(const GrB_Index *likes_comment_array_first,
                                                const GrB_Index *likes_comment_array_last,
                                                const GrB_Index *likes_user_array_first, queue_type &top_scores) const {
        // find tuple sequences of each comment in row-major array
        for (GrB_Index comment_col = 0; comment_col < input.comments_size(); ++comment_col) {
            compute_score_for_comment(input, comment_col, likes_comment_array_first, likes_comment_array_last,
                                      likes_user_array_first, top_scores);
        }
    }

    virtual queue_type init_top_scores() {
        return {};
    }

    queue_type calculate_score() {
        queue_type top_scores = init_top_scores();

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

        compute_score_for_all_comments(likes_comment_array_first, likes_comment_array_last,
                                       likes_user_array_first, top_scores);

        // if comments with likes are not enough collect comments without like
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
                    // if this comment wasn't checked before
                    top_scores.push(std::make_tuple(0, input.comments[comment_col].timestamp, comment_col));
                }

                if (top_scores.size() > 3)
                    top_scores.pop();
            }
        }

        return top_scores;
    }

    std::vector<uint64_t> initial_calculation() override {
        return convert_score_type_to_comment_id(calculate_score(), input);
    }

    std::vector<uint64_t> update_calculation(int iteration,
                                             const std::vector<Friends_Update> &friends_updates,
                                             const std::vector<Likes_Update> &likes_updates) override {
        return convert_score_type_to_comment_id(calculate_score(), input);
    }
};
