#pragma once

#include <chrono>
#include <utility>
#include "load.h"
#include "BaseSolution.h"

template<typename InputT, typename Update_Type>
class Solution : public BaseSolution {
protected:
    using queue_type = std::priority_queue<score_type, std::vector<score_type>, std::greater<>>;

    BenchmarkParameters parameters;
    InputT input;
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

    virtual void load_updates(int iteration, Update_Type &current_updates) {
        input.load_and_apply_updates(iteration, current_updates, parameters);
    }

    void update(int iteration) override {
        using namespace std::chrono;
        auto update_start = high_resolution_clock::now();

        Update_Type current_updates;
        load_updates(iteration, current_updates);
        std::vector<uint64_t> top_scores_vector = update_calculation(iteration, current_updates);

        report(parameters, iteration, BenchmarkPhase::Update,
               round<nanoseconds>(high_resolution_clock::now() - update_start),
               top_scores_vector);
    }

    virtual std::vector<uint64_t> initial_calculation() = 0;

    virtual std::vector<uint64_t> update_calculation(int iteration, const Update_Type &current_updates) = 0;

    void free() override {
        input.free();
    }
};
