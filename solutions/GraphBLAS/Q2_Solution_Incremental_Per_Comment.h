#pragma once

#include <queue>
#include <algorithm>
#include <cassert>
#include <numeric>
#include <memory>
#include <set>
#include "utils.h"
#include "load.h"
#include "Q2_Solution_Batch.h"

class Q2_Solution_Incremental_Per_Comment : public Q2_Solution_Batch {
    std::vector<score_type> last_result;
    std::optional<std::reference_wrapper<const Update_Type_Q2>> current_updates_opt;

public:
    using Q2_Solution_Batch::Q2_Solution_Batch;

    std::vector<uint64_t> initial_calculation() override {
        last_result = calculate_score();
        return convert_score_type_to_comment_id(last_result, input);
    }

    std::vector<GrB_Index> get_affected_comment_cols() const {
        std::vector<Friends_Update> friends_updates = current_updates_opt.value().get().friends_updates;
        std::vector<Likes_Update> likes_updates = current_updates_opt.value().get().likes_updates;
        std::vector<GrB_Index> new_comments = current_updates_opt.value().get().new_comments;

        if (friends_updates.empty() && likes_updates.empty() && new_comments.empty())
            return {};

        GrB_Object_cpp<GrB_Vector> affected_comments =
                GB(GrB_Vector_new, GrB_BOOL, input.comments_size());

        if (!likes_updates.empty() || !new_comments.empty()) {
            std::vector<GrB_Index> liked_or_new_comments;
            liked_or_new_comments.reserve(likes_updates.size() + new_comments.size());
            std::transform(likes_updates.begin(), likes_updates.end(),
                           std::back_inserter(liked_or_new_comments),
                           [](const auto &likes_update) { return likes_update.comment_column; });
            std::copy(new_comments.begin(), new_comments.end(), std::back_inserter(liked_or_new_comments));

            ok(GrB_Vector_build_BOOL(affected_comments.get(),
                                     liked_or_new_comments.data(), array_of_true(liked_or_new_comments.size()).get(),
                                     liked_or_new_comments.size(),
                                     GrB_LOR));
        }

        if (!friends_updates.empty()) {
            GrB_Object_cpp<GrB_Matrix> new_friends_mx =
                    GB(GrB_Matrix_new, GrB_BOOL, input.users_size(), friends_updates.size());
            GrB_Object_cpp<GrB_Matrix> affected_comments_mx =
                    GB(GrB_Matrix_new, GrB_BOOL, input.comments_size(), friends_updates.size());

            GrB_Index new_friends_nnz = friends_updates.size() * 2;
            std::vector<GrB_Index> new_friends_rows, new_friends_columns;
            new_friends_rows.reserve(new_friends_nnz);
            new_friends_columns.reserve(new_friends_nnz);

            GrB_Index column = 0;
            for (auto[user1_column, user2_column]:friends_updates) {
                new_friends_rows.emplace_back(user1_column);
                new_friends_rows.emplace_back(user2_column);

                new_friends_columns.emplace_back(column);
                new_friends_columns.emplace_back(column);

                ++column;
            }
            ok(GrB_Matrix_build_BOOL(new_friends_mx.get(),
                                     new_friends_rows.data(), new_friends_columns.data(),
                                     array_of_true(new_friends_nnz).get(),
                                     new_friends_nnz, GrB_LOR));

            ok(GrB_mxm(affected_comments_mx.get(), GrB_NULL, GrB_NULL, GxB_LAND_LAND_BOOL,
                       input.likes_matrix_tran.get(), new_friends_mx.get(), GrB_NULL));

            ok(GrB_Matrix_reduce_BinaryOp(affected_comments.get(), GrB_NULL,
                                          GrB_LOR, GrB_LOR,
                                          affected_comments_mx.get(), GrB_NULL));
        }

        GrB_Index affected_comments_num;
        ok(GrB_Vector_nvals(&affected_comments_num, affected_comments.get()));

        std::vector<GrB_Index> affected_comments_vector(affected_comments_num);
        ok(GrB_Vector_extractTuples_BOOL(affected_comments_vector.data(), nullptr, &affected_comments_num,
                                         affected_comments.get()));
        assert(affected_comments_num == affected_comments_vector.size());

        return affected_comments_vector;
    }

    void compute_score_for_all_comments(const GrB_Index *likes_comment_array_first,
                                        const GrB_Index *likes_comment_array_last,
                                        const GrB_Index *likes_user_array_first,
                                        queue_type &top_scores) const override {
        if (!current_updates_opt.has_value()) {
            // for first run compute score for every comment
            Q2_Solution_Batch::compute_score_for_all_comments(likes_comment_array_first, likes_comment_array_last,
                                                              likes_user_array_first, top_scores);
        } else {
            const std::vector<GrB_Index> affected_comment_cols = get_affected_comment_cols();

            for (auto[score, timestamp, comment_col]:last_result) {
                if (score != 0 && // avoid caching and initiate scan for comments without likes
                    !std::binary_search(affected_comment_cols.begin(), affected_comment_cols.end(), comment_col))
                    // use last scores if still valid
                    top_scores.emplace(score, timestamp, comment_col);
            }

            for (GrB_Index comment_col : affected_comment_cols) {
                compute_score_for_comment(input, comment_col, likes_comment_array_first, likes_comment_array_last,
                                          likes_user_array_first, top_scores);
            }
        }
    }

    std::vector<uint64_t> update_calculation(int iteration, const Update_Type_Q2 &current_updates) override {
        current_updates_opt = current_updates;

        last_result = calculate_score();
        return convert_score_type_to_comment_id(last_result, input);
    }
};
