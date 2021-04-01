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
    std::optional<std::reference_wrapper<const Q2_Input::Update_Type>> current_updates_opt;

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

        GBxx_Object<GrB_Vector> affected_comments =
                GB(GrB_Vector_new, GrB_BOOL, input.comments_size());

        // new comments and comments with new likes should be (re)evaluated
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
            GrB_Index friends_updates_undirected_size = friends_updates.size() / 2;
            GBxx_Object<GrB_Matrix> new_friends_mx =
                    GB(GrB_Matrix_new, GrB_BOOL, input.users_size(), friends_updates_undirected_size);
            GBxx_Object<GrB_Matrix> affected_comments_mx =
                    GB(GrB_Matrix_new, GrB_UINT8, input.comments_size(), friends_updates_undirected_size);

            // edges are listed in both directions, but matrix contains only them only once
            GrB_Index new_friends_nnz = 2 * friends_updates_undirected_size;
            std::vector<GrB_Index> new_friends_rows, new_friends_columns;
            new_friends_rows.reserve(new_friends_nnz);
            new_friends_columns.reserve(new_friends_nnz);

            // incidence matrix for new friendships
            // for each new friendship put a column into the matrix
            // each column contains 2 true values at the users connected by that friend edge
            GrB_Index column = 0;
            for (auto[user1_column, user2_column]:friends_updates) {
                if (user1_column < user2_column) {
                    new_friends_rows.emplace_back(user1_column);
                    new_friends_rows.emplace_back(user2_column);

                    new_friends_columns.emplace_back(column);
                    new_friends_columns.emplace_back(column);

                    ++column;
                }
            }
            assert(column == friends_updates_undirected_size);
            assert(new_friends_rows.size() == new_friends_nnz);
            assert(new_friends_columns.size() == new_friends_nnz);
            ok(GrB_Matrix_build_BOOL(new_friends_mx.get(),
                                     new_friends_rows.data(), new_friends_columns.data(),
                                     array_of_true(new_friends_nnz).get(),
                                     new_friends_nnz, GrB_LOR));

            // each column of affected_comments_mx contains true for comments which are affected by the corresponding new friend edge
            // the 2 true values in each column of new_friends_mx select 2 columns of likes_matrix_tran,
            //      which contain comments having likes from the users (multiplication)
            // a comment is affected if both users like it
            // => we sum how many users - among the two selected in each column - likes each comment
            // result: -, 1, 2 in each cell
            ok(GrB_mxm(affected_comments_mx.get(), GrB_NULL, GrB_NULL, GxB_PLUS_TIMES_UINT8,
                       input.likes_matrix_tran.get(), new_friends_mx.get(), GrB_NULL));

            auto scalar2 = GB(GxB_Scalar_new, GrB_UINT8);
            ok(GxB_Scalar_setElement_UINT8(scalar2.get(), 2));

            // filter the matrix: only cells with 2 remain (only comments with likes from both users)
            ok(GxB_Matrix_select(affected_comments_mx.get(), GrB_NULL, GrB_NULL, GxB_EQ_THUNK,
                                 affected_comments_mx.get(), scalar2.get(), GrB_NULL));

            // comments which are affected because:
            // - they are new or got new like edges (already in affected_comments)
            // - at least one new friend edge affects it (true value(s) in their row in affected_comments_mx)
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

    void compute_score_for_all_comments(const GrB_Index *likes_comment_array_begin,
                                        const GrB_Index *likes_comment_array_end,
                                        const GrB_Index *likes_user_array_begin,
                                        std::vector<score_type> &top_scores) const override {
        if (!current_updates_opt.has_value()) {
            // for first run compute score for every comment
            Q2_Solution_Batch::compute_score_for_all_comments(likes_comment_array_begin, likes_comment_array_end,
                                                              likes_user_array_begin, top_scores);
        } else {
            const std::vector<GrB_Index> affected_comment_cols = get_affected_comment_cols();

            for (auto[score, timestamp, comment_col]:last_result) {
                if (score != 0 && // avoid caching and initiate reevaluation for comments without likes
                    !std::binary_search(affected_comment_cols.begin(), affected_comment_cols.end(), comment_col))
                    // use last scores if still valid
                    add_score_to_toplist(top_scores, std::make_tuple(score, timestamp, comment_col));
            }

            int nthreads;
            LAGraph_GetNumThreads(&nthreads, NULL);
#pragma omp parallel num_threads(nthreads)
            {
                std::vector<score_type> top_scores_local;

#pragma omp for schedule(dynamic)
                for (size_t i = 0; i < affected_comment_cols.size(); ++i) { // NOLINT(modernize-loop-convert)
                    compute_score_for_comment(input, affected_comment_cols[i], likes_comment_array_begin,
                                              likes_comment_array_end,
                                              likes_user_array_begin, top_scores_local);
                }

#pragma omp critical(Q2_add_score_to_toplist)
                for (auto score : top_scores_local) {
                    add_score_to_toplist(top_scores, score);
                }
            }
        }
    }

    std::vector<uint64_t> update_calculation(int iteration, const Q2_Input::Update_Type &current_updates) override {
        current_updates_opt = current_updates;

        last_result = calculate_score();
        return convert_score_type_to_comment_id(last_result, input);
    }
};
