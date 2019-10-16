#ifndef GRAPHBLAS_LOAD_H
#define GRAPHBLAS_LOAD_H

extern "C" {
#include <GraphBLAS.h>
#include <LAGraph.h>
}

#include <vector>
#include <map>
#include "utils.h"

struct Comment {
    uint64_t comment_id;
    time_t timestamp;

    Comment(uint64_t comment_id, time_t timestamp) : comment_id(comment_id), timestamp(timestamp) {}
};

struct Q2_Input {
    std::vector<Comment> comments;
    std::map<uint64_t, GrB_Index> comment_id_to_column;

    std::map<uint64_t, GrB_Index> user_id_to_column;

    GrB_Matrix likes_matrix_tran, friends_matrix;

    GrB_Index likes_num, friends_num;

    auto users_size() const {
        return user_id_to_column.size();
    }

    auto comments_size() const {
        return comments.size();
    }

    void free() {
        ok(GrB_Matrix_free(&likes_matrix_tran));
        ok(GrB_Matrix_free(&friends_matrix));
    }
};

Q2_Input load_initial(const BenchmarkParameters &parameters);

void
load_updates(std::vector<std::string> &types, int iteration, const BenchmarkParameters &parameters, Q2_Input &input);

#endif //GRAPHBLAS_LOAD_H
