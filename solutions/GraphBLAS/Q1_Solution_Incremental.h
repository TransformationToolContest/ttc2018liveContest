#pragma once

#include <queue>
#include <algorithm>
#include <cassert>
#include <numeric>
#include <memory>
#include <set>
#include "utils.h"
#include "load.h"
#include "Q1_Solution_Batch.h"

class Q1_Solution_Incremental : public Q1_Solution_Batch {
protected:
    std::vector<score_type> last_result;
    GBxx_Object<GrB_Vector> last_score_vec;
    std::optional<std::reference_wrapper<const Q1_Input::Update_Type>> updates_opt;

    GBxx_Object<GrB_Vector> get_score_vec() const override {
        if (updates_opt.has_value()) {
            Q1_Input::Update_Type const &updates = updates_opt.value();
            // compute additional score of new comments and likes
            // number of new comments is counted on new_root_post_tran
            // to get the root post for each new like we use the updated root_post_tran
            GBxx_Object<GrB_Vector> partial_score_vec = Q1_Solution_Batch::get_score_vec(
                    input.posts_size(),
                    updates.new_root_post_tran_NEW.get(), input.root_post_tran_NEW.get(),
                    updates.new_likes_count_vec.get());

            // update score vector by adding the additional score
            ok(GrB_Vector_eWiseAdd_BinaryOp(last_score_vec.get(), GrB_NULL, GrB_NULL,
                                            GrB_PLUS_UINT64, last_score_vec.get(), partial_score_vec.get(), GrB_NULL));
            // get updated scores for each post where changed, otherwise omit score
            // overwrite scores in partial score vector using partial score vector as mask
            ok(GrB_Vector_extract(partial_score_vec.get(), partial_score_vec.get(), GrB_NULL,
                                  last_score_vec.get(), GrB_ALL, 0, GrB_NULL));

            return partial_score_vec;
        } else {
            return Q1_Solution_Batch::get_score_vec();
        }
    }

    void save_score_vec(GBxx_Object<GrB_Vector> score_vec) override {
        // only save the last score_vec for the initial calculation
        // for updates it contains only partial result, full result is saved earlier
        if (!updates_opt.has_value())
            last_score_vec = std::move(score_vec);
    }

public:
    using Q1_Solution_Batch::Q1_Solution_Batch;

    void load_updates(int iteration, Q1_Input::Update_Type &current_updates) override {
        auto old_posts_size = input.posts_size();
        input.load_and_apply_updates(iteration, current_updates, parameters, false);

        if (input.posts_size() > old_posts_size) {
            ok(GxB_Vector_resize(last_score_vec.get(), input.posts_size()));
        }
    }

    std::vector<uint64_t> initial_calculation() override {
        last_result = calculate_score();
        return convert_score_type_to_post_id(last_result, input);
    }

public:
    std::vector<uint64_t> update_calculation(int iteration, const Q1_Input::Update_Type &current_updates) override {
        updates_opt = current_updates;

        auto new_result = calculate_score();

        // use last scores if still valid
        for (auto[score, timestamp, comment_col]:last_result) {
            if (std::none_of(new_result.begin(), new_result.end(),
                             [comment_col = comment_col](auto const &new_tuple) {
                                 return std::get<2>(new_tuple) == comment_col;
                             })) {
                new_result.emplace_back(score, timestamp, comment_col);
            }
        }
        std::sort(new_result.begin(), new_result.end(), std::greater<>{});
        new_result.resize(top_count);

        last_result = std::move(new_result);

        return convert_score_type_to_post_id(last_result, input);
    }
};
