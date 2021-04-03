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
    static std::vector<uint64_t>
    convert_score_type_to_comment_id(const std::vector<score_type> &top_scores, const Q2_Input &input) {
        std::vector<uint64_t> top_scores_vector;
        top_scores_vector.reserve(top_count);

        // convert row indices to original comment IDs
        std::transform(top_scores.rbegin(), top_scores.rend(), std::back_inserter(top_scores_vector),
                       [&input](const auto &score_tuple) {
                           return input.comments[std::get<2>(score_tuple)].comment_id;
                       });

        return top_scores_vector;
    }

    static inline void
    compute_score_for_comment(const Q2_Input &input, GrB_Index comment_col, const GrB_Index *likes_comment_array_begin,
                              const GrB_Index *likes_comment_array_end, const GrB_Index *likes_user_array_begin,
                              std::vector<score_type> &top_scores) __attribute__ ((always_inline)) {
        // find tuple sequences of each comment in row-major array
        // users liking a comment are stored consecutively
        auto[likes_comment_begin, likes_comment_end] = std::equal_range(likes_comment_array_begin,
                                                                        likes_comment_array_end, comment_col);
        if (likes_comment_begin != likes_comment_end) {
            GrB_Index likes_count = std::distance(likes_comment_begin, likes_comment_end);
            // get position of first user liking that comment
            const GrB_Index *likes_user_begin =
                    likes_user_array_begin + std::distance(likes_comment_array_begin, likes_comment_begin);

            // extract friendships submatrix of users liking the comment
            GBxx_Object<GrB_Matrix> friends_overlay_graph = GB(GrB_Matrix_new, GrB_BOOL, likes_count, likes_count);
            ok(GrB_Matrix_extract(friends_overlay_graph.get(), GrB_NULL, GrB_NULL,
                                  input.friends_matrix.get(),
                                  likes_user_begin, likes_count, likes_user_begin, likes_count,
                                  GrB_NULL));

            // assuming that all component_ids will be in [0, n)
            LAGraph_Graph G;
            GrB_Matrix A = friends_overlay_graph.get();
            LAGraph_New(&G, &A, LAGRAPH_ADJACENCY_UNDIRECTED, NULL);
            char msg [LAGRAPH_MSG_LEN];
            GBxx_Object<GrB_Vector> components_vector = LAGr(LAGraph_ConnectedComponents, G, msg);
            LAGraph_Delete(&G, NULL);

            GrB_Index nvals;
#ifndef NDEBUG
            nvals = input.likes_num;
            ok(GrB_Vector_nvals(&nvals, components_vector.get()));
            assert(nvals == likes_count);

            GrB_Index n;
            ok(GrB_Vector_size(&n, components_vector.get()));
            assert(n == likes_count);
#endif

            std::vector<uint64_t> components(likes_count),
                    component_sizes(likes_count);

            // nullptr: SuiteSparse extension
            nvals = likes_count;
            ok(GrB_Vector_extractTuples_UINT64(nullptr, components.data(), &nvals, components_vector.get()));
            assert(nvals == likes_count);

            // count size of each component
            for (auto component_id:components)
                ++component_sizes[component_id];

            std::transform(component_sizes.begin(), component_sizes.end(), component_sizes.begin(),
                           [](uint64_t n) { return n * n; });

            uint64_t score = std::accumulate(component_sizes.begin(), component_sizes.end(), uint64_t());

            add_score_to_toplist(top_scores,
                                 std::make_tuple(score, input.comments[comment_col].timestamp, comment_col));
        }
    }

public:
    using Q2_Solution::Q2_Solution;

    virtual void compute_score_for_all_comments(const GrB_Index *likes_comment_array_begin,
                                                const GrB_Index *likes_comment_array_end,
                                                const GrB_Index *likes_user_array_begin,
                                                std::vector<score_type> &top_scores) const {
        int nthreads;
        LAGraph_GetNumThreads(&nthreads, NULL);
#pragma omp parallel num_threads(nthreads)
        {
            std::vector<score_type> top_scores_local;

#pragma omp for schedule(dynamic)
            for (GrB_Index comment_col = 0; comment_col < input.comments_size(); ++comment_col) {
                compute_score_for_comment(input, comment_col, likes_comment_array_begin, likes_comment_array_end,
                                          likes_user_array_begin, top_scores_local);
            }

#pragma omp critical(Q2_add_score_to_toplist)
            for (auto score : top_scores_local) {
                add_score_to_toplist(top_scores, score);
            }
        }
    }

    std::vector<score_type> calculate_score() {
        std::vector<score_type> top_scores;

        std::unique_ptr<GrB_Index[]> likes_trg_comment_columns{new GrB_Index[input.likes_num]},
                likes_src_user_columns{new GrB_Index[input.likes_num]};
        GrB_Index *likes_comment_array_begin = likes_trg_comment_columns.get(),
                *likes_comment_array_end = likes_trg_comment_columns.get() + input.likes_num,
                *likes_user_array_begin = likes_src_user_columns.get();

        // nullptr to avoid extracting matrix values (SuiteSparse extension)
        GrB_Index nvals = input.likes_num;
        // extract likes edges row-wise, users liking a comment are stored consecutively
        ok(GrB_Matrix_extractTuples_BOOL(likes_trg_comment_columns.get(), likes_src_user_columns.get(), nullptr, &nvals,
                                         input.likes_matrix_tran.get()));
        assert(nvals == input.likes_num);

        compute_score_for_all_comments(likes_comment_array_begin, likes_comment_array_end, likes_user_array_begin,
                                       top_scores);

        // if comments with likes are not enough collect comments without like
        if (top_scores.size() < top_count) {
            for (GrB_Index comment_col = 0; comment_col < input.comments_size(); ++comment_col) {
                if (std::none_of(top_scores.begin(), top_scores.end(),
                                 [comment_col](auto const &tuple) { return std::get<2>(tuple) == comment_col; })) {
                    // try to add this comment if not present
                    add_score_to_toplist(top_scores,
                                         std::make_tuple(0, input.comments[comment_col].timestamp, comment_col));
                }
            }
        }

        sort_top_scores(top_scores);

        return top_scores;
    }

    std::vector<uint64_t> initial_calculation() override {
        return convert_score_type_to_comment_id(calculate_score(), input);
    }

    std::vector<uint64_t> update_calculation(int iteration, const Q2_Input::Update_Type &current_updates) override {
        return convert_score_type_to_comment_id(calculate_score(), input);
    }
};
