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

        // TODO

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
