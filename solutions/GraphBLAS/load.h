#pragma once

extern "C" {
#include <GraphBLAS.h>
#include <LAGraph.h>
}

#include <vector>
#include <map>
#include "utils.h"

struct Post {
    uint64_t post_id;
    time_t timestamp;

    Post(uint64_t post_id, time_t timestamp) : post_id(post_id), timestamp(timestamp) {}
};

struct Comment {
    uint64_t comment_id;
    time_t timestamp;

    Comment(uint64_t comment_id, time_t timestamp) : comment_id(comment_id), timestamp(timestamp) {}
};

struct Friends_Update {
    GrB_Index user1_column, user2_column;

    Friends_Update(GrB_Index user1Column, GrB_Index user2Column)
            : user1_column(user1Column), user2_column(user2Column) {}
};

struct Likes_Update {
    GrB_Index user_column, comment_column;

    Likes_Update(GrB_Index userColumn, GrB_Index commentColumn)
            : user_column(userColumn), comment_column(commentColumn) {}
};

struct Update_Type_Q1 {
//    std::vector<Friends_Update> friends_updates;
//    std::vector<Likes_Update> likes_updates;
//    std::vector<GrB_Index> new_comments;
};

struct Update_Type_Q2 {
    std::vector<Friends_Update> friends_updates;
    std::vector<Likes_Update> likes_updates;
    std::vector<GrB_Index> new_comments;
};

struct Q1_Input {
    std::vector<Post> posts;
    std::map<uint64_t, GrB_Index> post_id_to_column;

    std::map<uint64_t, GrB_Index> comment_id_to_column;

    GrB_Matrix root_post_tran;
    GrB_Vector likes_count_vec;

    GrB_Index root_post_num, likes_count_num;

    auto posts_size() const {
        return posts.size();
    }

    auto comments_size() const {
        return comment_id_to_column.size();
    }

    void free() {
        ok(GrB_Matrix_free(&root_post_tran));
        ok(GrB_Vector_free(&likes_count_vec));
    }

    static Q1_Input load_initial(const BenchmarkParameters &parameters);

    void load_and_apply_updates(int iteration, Update_Type_Q1 &current_updates, const BenchmarkParameters &parameters);
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

    static Q2_Input load_initial(const BenchmarkParameters &parameters);

    void load_and_apply_updates(int iteration, Update_Type_Q2 &current_updates, const BenchmarkParameters &parameters);
};
