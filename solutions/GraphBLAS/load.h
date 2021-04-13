#pragma once

extern "C" {
#include <GraphBLAS.h>
#include <LAGraph.h>
}

#include <vector>
#include <map>
#include <cassert>
#include "utils.h"
#include "gb_utils.h"

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

struct Root_Post_Update {
    GrB_Index comment_col, post_col;

    Root_Post_Update(GrB_Index commentCol, GrB_Index postCol) : comment_col(commentCol), post_col(postCol) {}
};

struct Q1_Input {
    std::vector<Post> posts;
    std::map<uint64_t, GrB_Index> post_id_to_column;

    std::map<uint64_t, GrB_Index> comment_id_to_column;

    GBxx_Object<GrB_Matrix> root_post_tran, root_post_tran_NEW;
    GBxx_Object<GrB_Vector> likes_count_vec;

    GrB_Index root_post_num, likes_count_num;

    GrB_Index root_post_num_NEW;

    auto posts_size() const {
        assert(posts.size() == post_id_to_column.size());
        return posts.size();
    }

    auto comments_size() const {
        return comment_id_to_column.size();
    }

    static Q1_Input load_initial(const BenchmarkParameters &parameters);

    struct Update_Type {
        GBxx_Object<GrB_Matrix> new_root_post_tran;
        GBxx_Object<GrB_Matrix> new_root_post_tran_NEW;
        GBxx_Object<GrB_Vector> new_likes_count_vec;
    };

    void load_and_apply_updates(int iteration, Update_Type &updates, const BenchmarkParameters &parameters,
                                bool apply_likes_updates = true);
};

struct Q2_Input {
    std::vector<Comment> comments;
    std::map<uint64_t, GrB_Index> comment_id_to_column;

    std::map<uint64_t, GrB_Index> user_id_to_column;

    GBxx_Object<GrB_Matrix> likes_matrix_tran, friends_matrix;

    GrB_Index likes_num, friends_num;

    auto users_size() const {
        return user_id_to_column.size();
    }

    auto comments_size() const {
        return comments.size();
    }

    static Q2_Input load_initial(const BenchmarkParameters &parameters);

    struct Update_Type {
        std::vector<Friends_Update> friends_updates;
        std::vector<Likes_Update> likes_updates;
        std::vector<GrB_Index> new_comments;
    };

    void load_and_apply_updates(int iteration, Update_Type &updates, const BenchmarkParameters &parameters);
};
