#include <fstream>
#include <ctime>
#include <memory>
#include <iostream>
#include <iomanip>
#include "load.h"
#include <cassert>
#include "utils.h"

template<char Delimiter>
struct ignore_until {
};

constexpr ignore_until<'\n'> ignore_line;
constexpr ignore_until<'|'> ignore_field;

template<typename _CharT, typename _Traits, char Delimiter>
std::basic_istream<_CharT, _Traits> &
operator>>(std::basic_istream<_CharT, _Traits> &stream, const ignore_until<Delimiter> &) {
    return stream.ignore(std::numeric_limits<std::streamsize>::max(), Delimiter);
}

bool read_comment_or_post_line(std::ifstream &file, uint64_t &id, time_t &timestamp) {
    char delimiter;
    const char *timestamp_format = "%Y-%m-%d %H:%M:%S";

    std::tm t = {};
    if (!(file >> id >> delimiter >> std::get_time(&t, timestamp_format) >> ignore_line))
        return false;

    // depending on current time zone
    // it's acceptable since we only use these for ordering
    timestamp = std::mktime(&t);

    return true;
}

bool read_post_line(std::ifstream &posts_file, Q1_Input &input, GrB_Index &post_col) {
    uint64_t post_id;
    time_t timestamp;
    if (!read_comment_or_post_line(posts_file, post_id, timestamp))
        return false;

    post_col = input.post_id_to_column.emplace(post_id, input.posts.size()).first->second;
    input.posts.emplace_back(post_id, timestamp);

    return true;
}

bool read_post_line(std::ifstream &posts_file, Q1_Input &input) {
    GrB_Index post_col;
    return read_post_line(posts_file, input, post_col);
}

enum SubmissionType : char {
    Post, Comment
};

bool read_comment_line_root_post(GrB_Index &comment_col, GrB_Index &post_col,   // TODO: remove post_col
                                 std::pair<SubmissionType, GrB_Index> &previous_col, std::ifstream &comments_file,
                                 Q1_Input &input) {
    // Comment format:
    // id, ts, content, submitterid, previousid, postid
    char delimiter;
    uint64_t comment_id, previous_id, post_id;  // TODO: remove post_id

    if (!(comments_file
            >> comment_id >> delimiter
            >> ignore_field
            >> ignore_field
            >> ignore_field
            >> previous_id >> delimiter
            >> post_id))
        return false;

    comment_col = input.comment_id_to_column.emplace(comment_id, input.comment_id_to_column.size()).first->second;

    post_col = input.post_id_to_column.find(post_id)->second;

    auto root_post_iterator = input.post_id_to_column.find(previous_id);
    if (root_post_iterator == input.post_id_to_column.end())
        // previous Submission is a Comment, insert if seen first time
        previous_col = {Comment, input.comment_id_to_column.emplace(previous_id, input.comment_id_to_column.size())
                .first->second};
    else {
        // previous Submission is the root Post
        previous_col = {Post, root_post_iterator->second};
        assert(previous_col.second == post_col);
    }

    return true;
}

bool read_comment_line(std::ifstream &comments_file, Q2_Input &input, GrB_Index &comment_col) {
    uint64_t comment_id;
    time_t timestamp;
    if (!read_comment_or_post_line(comments_file, comment_id, timestamp))
        return false;

    comment_col = input.comments.size();
    input.comment_id_to_column.emplace(comment_id, comment_col);
    input.comments.emplace_back(comment_id, timestamp);

    return true;
}

bool read_comment_line(std::ifstream &comments_file, Q2_Input &input) {
    GrB_Index comment_col;
    return read_comment_line(comments_file, input, comment_col);
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

bool read_likes_line(GrB_Index &comment_column, std::ifstream &likes_file, Q1_Input &input) {
    uint64_t comment_id;
    if (!(likes_file >> ignore_field >> comment_id))
        return false;

    comment_column = input.comment_id_to_column.find(comment_id)->second;

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

std::ifstream get_change_file(const BenchmarkParameters &parameters, int iteration) {
    std::stringstream change_path;
    change_path << parameters.ChangePath << "/change"
                << std::setfill('0') << std::setw(2)
                << iteration << ".csv";

    std::ifstream change_file{change_path.str()};
    if (!change_file) {
        throw std::runtime_error{"Failed to open input file"};
    }

    return change_file;
}

Q1_Input Q1_Input::load_initial(const BenchmarkParameters &parameters) {
    std::string posts_path = parameters.ChangePath + "/csv-posts-initial.csv",
            comments_path = parameters.ChangePath + "/csv-comments-initial.csv",
            likes_path = parameters.ChangePath + "/csv-likes-initial.csv";

    std::ifstream
            posts_file{posts_path},
            comments_file{comments_path},
            likes_file{likes_path};
    if (!(posts_file && comments_file && likes_file)) {
        throw std::runtime_error{"Failed to open input files"};
    }

    Q1_Input input;

    // read posts
    while (read_post_line(posts_file, input));

    // read root_post edges
    std::vector<GrB_Index> root_post_src_comment_columns, root_post_trg_post_columns;
    // (:Comment)-[:COMMENTED]->(:Post) = (:Comment)-[:ROOT_POST]->(:Post) edges
    std::vector<GrB_Index> root_post_src_comment_columns_NEW, root_post_trg_post_columns_NEW;
    // (:Comment)-[:COMMENTED]->(:Comment) edges
    std::vector<GrB_Index> commented_src_comment_columns, commented_trg_comment_columns;
    {
        GrB_Index comment_col, post_col;
        std::pair<SubmissionType, GrB_Index> previous_col;
        while (read_comment_line_root_post(comment_col, post_col, previous_col, comments_file, input)) {
            root_post_src_comment_columns.emplace_back(comment_col);
            root_post_trg_post_columns.emplace_back(post_col);

            switch (previous_col.first) {
                case Post:
                    root_post_src_comment_columns_NEW.emplace_back(comment_col);
                    root_post_trg_post_columns_NEW.emplace_back(previous_col.second);
                    break;
                case Comment:
                    commented_src_comment_columns.emplace_back(comment_col);
                    commented_trg_comment_columns.emplace_back(previous_col.second);
                    break;
            }
        }
    }

    std::vector<GrB_Index> likes_count_columns;
    std::vector<uint64_t> likes_count_values;
    {
        std::map<GrB_Index, uint64_t> likes_count_map;
        GrB_Index comment_col;
        while (read_likes_line(comment_col, likes_file, input)) {
            ++likes_count_map[comment_col];
        }

        likes_count_columns.reserve(likes_count_map.size());
        likes_count_values.reserve(likes_count_map.size());

        for (auto[column, value]:likes_count_map) {
            likes_count_columns.push_back(column);
            likes_count_values.push_back(value);
        }
    }

    input.root_post_num = root_post_src_comment_columns.size();
    input.root_post_tran = GB(GrB_Matrix_new, GrB_BOOL, input.posts_size(), input.comments_size());
    ok(GrB_Matrix_build_BOOL(input.root_post_tran.get(),
                             root_post_trg_post_columns.data(), root_post_src_comment_columns.data(),
                             array_of_true(input.root_post_num).get(),
                             input.root_post_num, GrB_LOR));

    input.root_post_num_NEW = root_post_src_comment_columns_NEW.size();
    input.root_post_tran_NEW = GB(GrB_Matrix_new, GrB_BOOL, input.posts_size(), input.comments_size());
    ok(GrB_Matrix_build_BOOL(input.root_post_tran_NEW.get(),
                             root_post_trg_post_columns_NEW.data(), root_post_src_comment_columns_NEW.data(),
                             array_of_true(input.root_post_num_NEW).get(),
                             input.root_post_num_NEW, GrB_LOR));

    GrB_Index commented_num = commented_src_comment_columns.size();
    auto commented_tran = GB(GrB_Matrix_new, GrB_BOOL, input.comments_size(), input.comments_size());
    ok(GrB_Matrix_build_BOOL(commented_tran.get(),
                             commented_trg_comment_columns.data(), commented_src_comment_columns.data(),
                             array_of_true(commented_num).get(),
                             commented_num, GrB_LOR));

    auto next_root_post_tran = GB(GrB_Matrix_new, GrB_BOOL, input.posts_size(), input.comments_size());
    // start MSBFS from Comments commented on their root_post directly
    GrB_Matrix next_mx_input = input.root_post_tran_NEW.get();

    while (true) {
        ok(GrB_mxm(next_root_post_tran.get(), input.root_post_tran_NEW.get(), GrB_NULL,
                   GxB_ANY_PAIR_BOOL, next_mx_input, commented_tran.get(), GrB_DESC_RSC));
        // continue later iterations from newly reached vertices
        next_mx_input = next_root_post_tran.get();

        // if no more Comments reachable
        if (GBxx_nvals(next_root_post_tran) == 0)
            break;

        ok(GrB_Matrix_eWiseAdd_BinaryOp(input.root_post_tran_NEW.get(), GrB_NULL, GrB_NULL,
                                        GxB_PAIR_BOOL, input.root_post_tran_NEW.get(), next_root_post_tran.get(),
                                        GrB_NULL));
    }

    bool equals;
    assert((ok(LAGraph_IsEqual(&equals, input.root_post_tran.get(), input.root_post_tran_NEW.get(), nullptr, nullptr)),
            equals));

    input.likes_count_num = likes_count_columns.size();
    input.likes_count_vec = GB(GrB_Vector_new, GrB_UINT64, input.comments_size());
    ok(GrB_Vector_build_UINT64(input.likes_count_vec.get(),
                               likes_count_columns.data(), likes_count_values.data(),
                               input.likes_count_num, GrB_PLUS_UINT64));

    // make sure tuples are in row-major order (SuiteSparse extension)
    GxB_Format_Value format;
    ok(GxB_Matrix_Option_get(input.root_post_tran.get(), GxB_FORMAT, &format));
    if (format != GxB_BY_ROW) {
        throw std::runtime_error{"Matrix is not CSR"};
    }

    return input;
}

void Q1_Input::load_and_apply_updates(int iteration, Update_Type &updates, const BenchmarkParameters &parameters,
                                      bool apply_likes_updates) {
    std::ifstream change_file = get_change_file(parameters, iteration);

    auto old_posts_size = posts_size(),
            old_comments_size = comments_size();

    std::array<char, 8 + 1> change_type;  // NOLINT(cppcoreguidelines-pro-type-member-init,hicpp-member-init)
    while (change_file >> std::ws, change_file.getline(change_type.data(), change_type.size(), '|')) {
        if (strcmp(change_type.data(), "Posts") == 0) {
            GrB_Index post_col;
            read_post_line(change_file, *this, post_col);

            updates.new_posts.emplace_back(post_col);
        } else if (strcmp(change_type.data(), "Comments") == 0) {
            GrB_Index comment_col, post_col;
            std::pair<SubmissionType, GrB_Index> previous_col; // TODO: use
            read_comment_line_root_post(comment_col, post_col, previous_col, change_file, *this);

            updates.new_root_post_src_comment_columns.push_back(comment_col);
            updates.new_root_post_trg_post_columns.push_back(post_col);
        } else if (strcmp(change_type.data(), "Likes") == 0) {
            GrB_Index comment_column;
            read_likes_line(comment_column, change_file, *this);

            updates.new_likes_to_comments.emplace_back(comment_column);
        } else if (strcmp(change_type.data(), "Friends") == 0
                   || strcmp(change_type.data(), "Users") == 0)
            // noop
            change_file >> ignore_line;
        else
            throw std::runtime_error{std::string{"Unknown change type: "} + change_type.data()};
    }

    if (posts_size() > old_posts_size
        || comments_size() > old_comments_size) {
        ok(GxB_Matrix_resize(root_post_tran.get(), posts_size(), comments_size()));
    }
    if (apply_likes_updates
        && comments_size() > old_comments_size) {
        ok(GxB_Vector_resize(likes_count_vec.get(), comments_size()));
    }

    updates.new_root_post_tran = GB(GrB_Matrix_new, GrB_BOOL, posts_size(), comments_size());
    updates.new_likes_count_vec = GB(GrB_Vector_new, GrB_UINT64, comments_size());

    if (!updates.new_root_post_src_comment_columns.empty()) {
        GrB_Index new_root_post_nvals = updates.new_root_post_src_comment_columns.size();
        ok(GrB_Matrix_build_BOOL(updates.new_root_post_tran.get(),
                                 updates.new_root_post_trg_post_columns.data(),
                                 updates.new_root_post_src_comment_columns.data(),
                                 array_of_true(new_root_post_nvals).get(),
                                 new_root_post_nvals, GrB_LOR));
        root_post_num += new_root_post_nvals;

        ok(GrB_Matrix_eWiseAdd_BinaryOp(root_post_tran.get(), GrB_NULL, GrB_NULL,
                                        GrB_LOR, root_post_tran.get(), updates.new_root_post_tran.get(), GrB_NULL));
    }

    if (!updates.new_likes_to_comments.empty()) {
        std::vector<uint64_t> new_likes_count_vec_vals(updates.new_likes_to_comments.size(), 1);
        ok(GrB_Vector_build_UINT64(updates.new_likes_count_vec.get(),
                                   updates.new_likes_to_comments.data(), new_likes_count_vec_vals.data(),
                                   updates.new_likes_to_comments.size(), GrB_PLUS_UINT64));

        if (apply_likes_updates) {
            ok(GrB_Vector_eWiseAdd_BinaryOp(likes_count_vec.get(), GrB_NULL, GrB_NULL,
                                            GrB_PLUS_UINT64, likes_count_vec.get(), updates.new_likes_count_vec.get(),
                                            GrB_NULL));

            GrB_Index nvals;
            ok(GrB_Vector_nvals(&nvals, likes_count_vec.get()));
            likes_count_num = nvals;
        }
    }
}

Q2_Input Q2_Input::load_initial(const BenchmarkParameters &parameters) {
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

    Q2_Input input;

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

    input.likes_matrix_tran = GB(GrB_Matrix_new, GrB_BOOL, input.comments_size(), input.users_size());
    ok(GrB_Matrix_build_BOOL(input.likes_matrix_tran.get(),
                             likes_trg_comment_columns.data(), likes_src_user_columns.data(),
                             array_of_true(input.likes_num).get(),
                             input.likes_num, GrB_LOR));

    input.friends_num = friends_src_columns.size();

    input.friends_matrix = GB(GrB_Matrix_new, GrB_BOOL, input.users_size(), input.users_size());
    ok(GrB_Matrix_build_BOOL(input.friends_matrix.get(),
                             friends_src_columns.data(), friends_trg_columns.data(),
                             array_of_true(input.friends_num).get(),
                             input.friends_num, GrB_LOR));

    // make sure tuples are in row-major order (SuiteSparse extension)
    GxB_Format_Value format;
    ok(GxB_Matrix_Option_get(input.likes_matrix_tran.get(), GxB_FORMAT, &format));
    if (format != GxB_BY_ROW) {
        throw std::runtime_error{"Matrix is not CSR"};
    }

    return input;
}

void
Q2_Input::load_and_apply_updates(int iteration, Q2_Input::Update_Type &updates, const BenchmarkParameters &parameters) {
    std::ifstream change_file = get_change_file(parameters, iteration);

    auto old_users_size = users_size(),
            old_comments_size = comments_size();

    std::array<char, 8 + 1> change_type;  // NOLINT(cppcoreguidelines-pro-type-member-init,hicpp-member-init)
    while (change_file >> std::ws, change_file.getline(change_type.data(), change_type.size(), '|')) {
        if (strcmp(change_type.data(), "Friends") == 0) {
            GrB_Index user1_column, user2_column;
            read_friends_line(user1_column, user2_column, change_file, *this);

            updates.friends_updates.emplace_back(user1_column, user2_column);
        } else if (strcmp(change_type.data(), "Likes") == 0) {
            GrB_Index user_column, comment_column;
            read_likes_line(user_column, comment_column, change_file, *this);

            updates.likes_updates.emplace_back(user_column, comment_column);
        } else if (strcmp(change_type.data(), "Comments") == 0) {
            GrB_Index comment_col;
            read_comment_line(change_file, *this, comment_col);
            updates.new_comments.emplace_back(comment_col);
        } else if (strcmp(change_type.data(), "Posts") == 0
                   || strcmp(change_type.data(), "Users") == 0)
            // noop for posts and users
            // new users will be processed when a connecting edge is added
            change_file >> ignore_line;
        else
            throw std::runtime_error{std::string{"Unknown change type: "} + change_type.data()};
    }

    if (users_size() > old_users_size) {
        ok(GxB_Matrix_resize(friends_matrix.get(), users_size(), users_size()));
    }
    if (comments_size() > old_comments_size
        || users_size() > old_users_size) {
        ok(GxB_Matrix_resize(likes_matrix_tran.get(), comments_size(), users_size()));
    }

#ifndef NDEBUG
    GrB_Index nvals;
    ok(GrB_Matrix_nvals(&nvals, friends_matrix.get()));
    assert(nvals == friends_num);
    ok(GrB_Matrix_nvals(&nvals, likes_matrix_tran.get()));
    assert(nvals == likes_num);
# endif

    for (auto[user1_column, user2_column] : updates.friends_updates) {
        ok(GrB_Matrix_setElement_BOOL(friends_matrix.get(), true, user1_column, user2_column));
    }
    friends_num += updates.friends_updates.size();

    for (auto[user_column, comment_column] : updates.likes_updates) {
        ok(GrB_Matrix_setElement_BOOL(likes_matrix_tran.get(), true, comment_column, user_column));
    }
    likes_num += updates.likes_updates.size();

#ifndef NDEBUG
    ok(GrB_Matrix_nvals(&nvals, friends_matrix.get()));
    assert(nvals == friends_num);
    ok(GrB_Matrix_nvals(&nvals, likes_matrix_tran.get()));
    assert(nvals == likes_num);
#endif
    // finish pending updates before parallel execution
    GrB_Matrix likes_matrix_tran_ptr = likes_matrix_tran.release();
    GrB_Matrix friends_matrix_ptr = friends_matrix.release();
    GrB_Matrix_wait(&likes_matrix_tran_ptr);
    GrB_Matrix_wait(&friends_matrix_ptr);
    likes_matrix_tran.reset(likes_matrix_tran_ptr);
    friends_matrix.reset(friends_matrix_ptr);
}
