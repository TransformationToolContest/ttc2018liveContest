#include <fstream>
#include <ctime>
#include <memory>
#include <iostream>
#include <iomanip>
#include "load.h"
#include <cassert>
#include "utils.h"

bool read_comment_line(std::ifstream &comments_file, Q2_Input &input) {
    char delimiter;
    const char *timestamp_format = "%Y-%m-%d %H:%M:%S";

    uint64_t comment_id;
    std::tm t = {};
    if (!(comments_file >> comment_id >> delimiter >> std::get_time(&t, timestamp_format)))
        return false;

    // depending on current time zone
    // it's acceptable since we only use these for ordering
    time_t timestamp = std::mktime(&t);

    // ignore remaining columns
    comments_file.ignore(std::numeric_limits<std::streamsize>::max(), '\n');

    GrB_Index column = input.comments.size();
    input.comment_id_to_column.emplace(comment_id, column);
    input.comments.emplace_back(comment_id, timestamp);

    return true;
}

bool read_friends_line(GrB_Index &user1_column, GrB_Index &user2_column, std::ifstream &friends_file, Q2_Input &input) {
    char delimiter;
    uint64_t user1_id, user2_id;
    if (!(friends_file >> user1_id >> delimiter >> user2_id))
        return false;

    user1_column = input.user_id_to_column.emplace(user1_id, input.user_id_to_column.size()).first->second;
    user2_column = input.user_id_to_column.emplace(user2_id, input.user_id_to_column.size()).first->second;

    return true;
}

bool read_likes_line(GrB_Index &user_column, GrB_Index &comment_column, std::ifstream &likes_file, Q2_Input &input) {
    char delimiter;
    uint64_t user_id, comment_id;
    if (!(likes_file >> user_id >> delimiter >> comment_id))
        return false;

    // new users can be added here, since friends file only added users with friends
    user_column = input.user_id_to_column.emplace(user_id, input.user_id_to_column.size())
            .first->second;
    comment_column = input.comment_id_to_column.find(comment_id)->second;

    return true;
}

Q2_Input load_initial(const BenchmarkParameters &parameters) {
    std::string comments_path = parameters.ChangePath + "/csv-comments-initial.csv";
    std::string friends_path = parameters.ChangePath + "/csv-friends-initial.csv";
    std::string likes_path = parameters.ChangePath + "/csv-likes-initial.csv";

    std::ifstream
            comments_file{comments_path},
            friends_file{friends_path},
            likes_file{likes_path};
    if (!(comments_file && friends_file && likes_file)) {
        throw std::runtime_error{"Failed to open input files"};
    }

    Q2_Input input{};

    char delimiter;
    while (read_comment_line(comments_file, input));

    std::vector<GrB_Index> friends_src_columns, friends_trg_columns;
    GrB_Index user1_column, user2_column;
    while (read_friends_line(user1_column, user2_column, friends_file, input)) {
        friends_src_columns.emplace_back(user1_column);
        friends_trg_columns.emplace_back(user2_column);
    }

    std::vector<GrB_Index> likes_src_user_columns, likes_trg_comment_columns;
    GrB_Index user_column, comment_column;
    while (read_likes_line(user_column, comment_column, likes_file, input)) {
        likes_src_user_columns.emplace_back(user_column);
        likes_trg_comment_columns.emplace_back(comment_column);
    }

    input.likes_num = likes_src_user_columns.size();
    std::unique_ptr<bool[]> likes_values{new bool[input.likes_num]};
    std::fill_n(likes_values.get(), input.likes_num, true);

    ok(GrB_Matrix_new(&input.likes_matrix_tran, GrB_BOOL, input.comments_size(), input.users_size()));
    ok(GrB_Matrix_build_BOOL(input.likes_matrix_tran,
                             likes_trg_comment_columns.data(), likes_src_user_columns.data(), likes_values.get(),
                             input.likes_num, GrB_LOR));

    input.friends_num = friends_src_columns.size();
    std::unique_ptr<bool[]> friends_values{new bool[input.friends_num]};
    std::fill_n(friends_values.get(), input.friends_num, true);

    ok(GrB_Matrix_new(&input.friends_matrix, GrB_BOOL, input.users_size(), input.users_size()));
    ok(GrB_Matrix_build_BOOL(input.friends_matrix,
                             friends_src_columns.data(), friends_trg_columns.data(), friends_values.get(),
                             input.friends_num, GrB_LOR));

    // make sure tuples are in row-major order (SuiteSparse extension)
    GxB_Format_Value format;
    ok(GxB_Matrix_Option_get(input.likes_matrix_tran, GxB_FORMAT, &format));
    if (format != GxB_BY_ROW) {
        throw std::runtime_error{"Matrix is not CSR"};
    }

    return input;
}

void
load_updates(std::vector<std::string> &types, int iteration, const BenchmarkParameters &parameters, Q2_Input &input) {
    std::stringstream change_path;
    change_path << parameters.ChangePath << "/change"
                << std::setfill('0') << std::setw(2)
                << iteration << ".csv";

    std::ifstream change_file{change_path.str()};
    if (!change_file) {
        throw std::runtime_error{"Failed to open input file"};
    }

    auto old_users_size = input.users_size(),
            old_comments_size = input.comments_size();
    std::vector<std::pair<GrB_Index, GrB_Index>> friends_updates, likes_updates;

    std::array<char, 8 + 1> change_type;  // NOLINT(cppcoreguidelines-pro-type-member-init,hicpp-member-init)
    while (change_file >> std::ws, change_file.getline(change_type.data(), change_type.size(), '|')) {
        types.emplace_back(change_type.data());

        if (strcmp(change_type.data(), "Friends") == 0) {
            GrB_Index user1_column, user2_column;
            read_friends_line(user1_column, user2_column, change_file, input);

            friends_updates.emplace_back(user1_column, user2_column);
        } else if (strcmp(change_type.data(), "Likes") == 0) {
            GrB_Index user_column, comment_column;
            read_likes_line(user_column, comment_column, change_file, input);

            likes_updates.emplace_back(user_column, comment_column);
        } else if (strcmp(change_type.data(), "Comments") == 0)
            read_comment_line(change_file, input);
        else if (strcmp(change_type.data(), "Posts") == 0
                 || strcmp(change_type.data(), "Users") == 0)
            // noop for posts and users
            // new users will be processed when a connecting edge is added
            change_file.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
        else
            throw std::runtime_error{std::string{"Unknown change type: "} + change_type.data()};
    }

    if (input.users_size() > old_users_size) {
        ok(GxB_Matrix_resize(input.friends_matrix, input.users_size(), input.users_size()));
    }
    if (input.comments_size() > old_comments_size
        || input.users_size() > old_users_size) {
        ok(GxB_Matrix_resize(input.likes_matrix_tran, input.comments_size(), input.users_size()));
    }

#ifndef NDEBUG
    GrB_Index nvals;
    ok(GrB_Matrix_nvals(&nvals, input.friends_matrix));
    assert(nvals == input.friends_num);
    ok(GrB_Matrix_nvals(&nvals, input.likes_matrix_tran));
    assert(nvals == input.likes_num);
# endif

    for (auto[user1_column, user2_column] : friends_updates) {
        ok(GrB_Matrix_setElement_BOOL(input.friends_matrix, true, user1_column, user2_column));
    }
    input.friends_num += friends_updates.size();

    for (auto[user_column, comment_column] : likes_updates) {
        ok(GrB_Matrix_setElement_BOOL(input.likes_matrix_tran, true, comment_column, user_column));
    }
    input.likes_num += likes_updates.size();

#ifndef NDEBUG
    ok(GrB_Matrix_nvals(&nvals, input.friends_matrix));
    assert(nvals == input.friends_num);
    ok(GrB_Matrix_nvals(&nvals, input.likes_matrix_tran));
    assert(nvals == input.likes_num);
#endif
}
