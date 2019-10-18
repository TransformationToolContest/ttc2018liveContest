#pragma once

#include <chrono>
#include <utility>
#include "load.h"

class Q2_Solution {
protected:
    BenchmarkParameters parameters;
    Q2_Input input;
public:
    explicit Q2_Solution(BenchmarkParameters parameters) : parameters{std::move(parameters)} {}

    void load() {
        using namespace std::chrono;
        auto load_start = high_resolution_clock::now();

        input = load_initial(parameters);

        report(parameters, 0, BenchmarkPhase::Load, round<nanoseconds>(high_resolution_clock::now() - load_start));
    }

    void initial() {
        using namespace std::chrono;
        auto initial_start = high_resolution_clock::now();

        std::vector<uint64_t> top_scores_vector = initial_calculation();

        report(parameters, 0, BenchmarkPhase::Initial, round<nanoseconds>(high_resolution_clock::now() - initial_start),
               top_scores_vector);
    }

    virtual void load_updates(int iteration, std::vector<Friends_Update> &friends_updates,
                              std::vector<Likes_Update> &likes_updates) {
        load_and_apply_updates(iteration, friends_updates, likes_updates, parameters, input);
    }

    void update(int iteration) {
        using namespace std::chrono;
        auto update_start = high_resolution_clock::now();

        std::vector<Friends_Update> friends_updates;
        std::vector<Likes_Update> likes_updates;
        load_updates(iteration, friends_updates, likes_updates);
        std::vector<uint64_t> top_scores_vector = update_calculation(iteration, friends_updates, likes_updates);

        report(parameters, iteration, BenchmarkPhase::Update,
               round<nanoseconds>(high_resolution_clock::now() - update_start),
               top_scores_vector);
    }

    virtual std::vector<uint64_t> initial_calculation() = 0;

    virtual std::vector<uint64_t>
    update_calculation(int iteration, const std::vector<Friends_Update> &friends_updates,
                       const std::vector<Likes_Update> &likes_updates) = 0;

    void free() {
        input.free();
    }
};
