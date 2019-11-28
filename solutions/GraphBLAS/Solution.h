#pragma once

#include <chrono>
#include <utility>
#include "load.h"
#include "BaseSolution.h"
#include "gb_utils.h"

template<typename InputT>
class Solution : public BaseSolution {
protected:
    BenchmarkParameters parameters;
    InputT input;
    inline static const int top_count = 3;

    static void add_score_to_toplist(std::vector<score_type> &top_scores, score_type score) {
        if (top_scores.size() < top_count || score > top_scores.front()) {
            top_scores.push_back(score);
            std::push_heap(top_scores.begin(), top_scores.end(), std::greater<>{});

            if (top_scores.size() > top_count) {
                std::pop_heap(top_scores.begin(), top_scores.end(), std::greater<>{});
                top_scores.pop_back();
            }
        }
    }

    static void sort_top_scores(std::vector<score_type> &top_scores) {
        std::sort_heap(top_scores.begin(), top_scores.end(), std::greater<>{});
    }

public:
    explicit Solution(BenchmarkParameters parameters) : parameters{std::move(parameters)} {}

    void load() override {
        using namespace std::chrono;
        auto load_start = high_resolution_clock::now();

        input = InputT::load_initial(parameters);

        report(parameters, 0, BenchmarkPhase::Load, round<nanoseconds>(high_resolution_clock::now() - load_start));
    }

    void initial() override {
        using namespace std::chrono;
        auto initial_start = high_resolution_clock::now();

        std::vector<uint64_t> top_scores_vector = initial_calculation();

        report(parameters, 0, BenchmarkPhase::Initial, round<nanoseconds>(high_resolution_clock::now() - initial_start),
               top_scores_vector);
    }

    virtual void load_updates(int iteration, typename InputT::Update_Type &current_updates) {
        input.load_and_apply_updates(iteration, current_updates, parameters);
    }

    void update(int iteration) override {
        using namespace std::chrono;
        auto update_start = high_resolution_clock::now();

        typename InputT::Update_Type current_updates;
        load_updates(iteration, current_updates);
        std::vector<uint64_t> top_scores_vector = update_calculation(iteration, current_updates);

        report(parameters, iteration, BenchmarkPhase::Update,
               round<nanoseconds>(high_resolution_clock::now() - update_start),
               top_scores_vector);
    }

    virtual std::vector<uint64_t> initial_calculation() = 0;

    virtual std::vector<uint64_t>
    update_calculation(int iteration, const typename InputT::Update_Type &current_updates) = 0;
};
