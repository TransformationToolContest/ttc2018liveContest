#include <iostream>
#include <fstream>
#include <stdexcept>

#include <algorithm>
#include <string>
#include <sstream>
#include <vector>
#include <map>
#include <ctime>
#include <iomanip>
#include <chrono>

#include "utils.h"

struct Comment {
    uint64_t comment_id;
    time_t timestamp;
};

struct Q2_Inputs {
    std::vector<Comment> comments;
    std::map<uint64_t, GrB_Index> comment_id_to_column;

    std::map<uint64_t, GrB_Index> user_id_to_column;

    GrB_Matrix likes_matrix, friends_matrix;

    auto users_size() const {
        return user_id_to_column.size();
    }

    auto comments_size() const {
        return comments.size();
    }

    void free() {
        ok(GrB_Matrix_free(&likes_matrix));
        ok(GrB_Matrix_free(&friends_matrix));
    }
};


int main(int argc, char **argv) {
    // temp
    ok(LAGraph_init());

    std::string base_folder = "../../models/1/";
    std::string comments_path = base_folder + "csv-comments-initial.csv";
    std::string friends_path = base_folder + "csv-friends-initial.csv";
    std::string likes_path = base_folder + "csv-likes-initial.csv";

    std::ifstream
            comments_file{comments_path},
            friends_file{friends_path},
            likes_file{likes_path};
    if (!(comments_file && friends_file && likes_file)) {
        throw std::runtime_error{"Failed to open input files"};
    }

    Q2_Inputs input{};

    char delimiter;
    {
        const char *timestamp_format = "%Y-%m-%d %H:%M:%S";
        uint64_t comment_id;
        std::tm t = {};
        while (comments_file >> comment_id >> delimiter >> std::get_time(&t, timestamp_format)) {
            // depending on current time zone
            // it's acceptable since we only use these for ordering
            time_t timestamp = std::mktime(&t);

            // ignore remaining columns
            comments_file.ignore(std::numeric_limits<std::streamsize>::max(), '\n');

            GrB_Index column = input.comments.size();
            input.comment_id_to_column.emplace(comment_id, column);
            input.comments.push_back(Comment{comment_id, timestamp});
        }
    }

    std::vector<GrB_Index> friends_src_columns, friends_trg_columns;
    {
        uint64_t user1_id, user2_id;
        while (friends_file >> user1_id >> delimiter >> user2_id) {
            GrB_Index user1_column = input.user_id_to_column.emplace(user1_id, input.user_id_to_column.size())
                    .first->second;
            GrB_Index user2_column = input.user_id_to_column.emplace(user2_id, input.user_id_to_column.size())
                    .first->second;

            friends_src_columns.push_back(user1_column);
            friends_trg_columns.push_back(user2_column);
        }
    }

    std::vector<GrB_Index> likes_src_user_columns, likes_trg_comment_columns;
    {
        uint64_t user_id, comment_id;
        while (likes_file >> user_id >> delimiter >> comment_id) {
            // new users can be added here, since friends file only added users with friends  
            GrB_Index user_column = input.user_id_to_column.emplace(user_id, input.user_id_to_column.size())
                    .first->second;
            GrB_Index comment_column = input.comment_id_to_column.find(comment_id)->second;

            likes_src_user_columns.push_back(user_column);
            likes_trg_comment_columns.push_back(comment_column);
        }
    }

    unsigned long likes_num = likes_src_user_columns.size();
    std::unique_ptr<bool[]> likes_values{new bool[likes_num]};
    std::fill_n(likes_values.get(), likes_num, true);

    ok(GrB_Matrix_new(&input.likes_matrix, GrB_BOOL, input.users_size(), input.comments_size()));
    ok(GrB_Matrix_build_BOOL(input.likes_matrix,
                             likes_src_user_columns.data(), likes_trg_comment_columns.data(), likes_values.get(),
                             likes_num, GrB_LOR));

    unsigned long friends_num = friends_src_columns.size();
    std::unique_ptr<bool[]> friends_values{new bool[friends_num]};
    std::fill_n(friends_values.get(), friends_num, true);

    ok(GrB_Matrix_new(&input.friends_matrix, GrB_BOOL, input.users_size(), input.users_size()));
    ok(GrB_Matrix_build_BOOL(input.friends_matrix,
                             friends_src_columns.data(), friends_trg_columns.data(), friends_values.get(),
                             friends_num, GrB_LOR));

    GrB_Index friends_nvals;
    ok(GrB_Matrix_nvals(&friends_nvals, input.friends_matrix));
    GrB_Index likes_nvals;
    ok(GrB_Matrix_nvals(&likes_nvals, input.likes_matrix));

    WriteOutDebugMatrix("friends", input.friends_matrix);

    std::cout << input.comments.size() << std::endl;
    std::cout << input.user_id_to_column.size() << "; " << friends_src_columns.size() << ", "
              << friends_trg_columns.size()
              << std::endl;
    std::cout << input.user_id_to_column.size() << "; " << std::endl;
    std::cout << likes_num << ", " << likes_trg_comment_columns.size() << std::endl;
    std::cout << std::endl;
    std::cout << friends_nvals << std::endl;
    std::cout << likes_nvals << std::endl;

    input.free();
}
