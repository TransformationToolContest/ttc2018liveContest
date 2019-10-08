#include <algorithm>
#include <memory>
#include <cassert>

extern "C" {
#include <GraphBLAS.h>
#include <LAGraph.h>
}

#include "utils.h"
#include "computation_timer.hpp"
#include "load.h"

GrB_Vector WeaklyConnectedComponents(GrB_Matrix A, bool directed) {
    ComputationTimer total_timer{"WeaklyConnectedComponents"};

    GrB_Index n;
    ok(GrB_Matrix_nrows(&n, A));

    GrB_Matrix C = A;
    if (directed) {
        ok((GrB_Matrix_new(&C, GrB_BOOL, n, n)));

        GrB_Descriptor desc;
        ok((GrB_Descriptor_new(&desc)));
        ok((GrB_Descriptor_set(desc, GrB_INP1, GrB_TRAN)));

        ok((GrB_eWiseAdd_Matrix_BinaryOp(C, GrB_NULL, GrB_NULL, GrB_LOR, A, A, desc)));
        ok((GrB_Descriptor_free(&desc)));
    } else {
        C = A;
    }
    GrB_Vector components = nullptr;
    ok(LAGraph_cc(C, &components));

    return components;
}

int main(int argc, char **argv) {
    BenchmarkParameters parameters = ParseBenchmarkParameters(argc, argv);

    ok(LAGraph_init());
    GxB_Global_Option_set(GxB_GLOBAL_NTHREADS, parameters.thread_num);

    Q2_Input input = load("../../models/2/");

    // make sure tuples are in row-major order (SuiteSparse extension)
    GxB_Format_Value format;
    ok(GxB_Matrix_Option_get(input.likes_matrix_tran, GxB_FORMAT, &format));
    if (format != GxB_BY_ROW) {
        throw std::runtime_error{"Matrix is not CSR"};
    }

    std::unique_ptr<GrB_Index[]> likes_trg_comment_columns{new GrB_Index[input.likes_num]},
            likes_src_user_columns{new GrB_Index[input.likes_num]};
    GrB_Index *likes_comment_array_last = likes_trg_comment_columns.get() + input.likes_num;

    // nullptr to avoid extracting matrix values (SuiteSparse extension)
    GrB_Index nvals = input.likes_num;
    ok(GrB_Matrix_extractTuples_BOOL(likes_trg_comment_columns.get(), likes_src_user_columns.get(), nullptr, &nvals,
                                     input.likes_matrix_tran));
    assert(nvals == input.likes_num);

    // find tuple sequences of each comment in row-major array
    GrB_Index *likes_comment_first = likes_trg_comment_columns.get();
    GrB_Index *likes_user_first = likes_src_user_columns.get();
    for (GrB_Index comment_col = 0; comment_col < input.comments_size(); ++comment_col) {
        if (likes_comment_first == likes_comment_array_last // no more values in likes matrix
            || comment_col < *likes_comment_first) {
            // no likes for this comment
            continue;
        } else {
            GrB_Index *likes_comment_last = likes_comment_first;
            GrB_Index *likes_user_last = likes_user_first;
            GrB_Index likes_count = 0;
            for (; likes_comment_last != likes_comment_array_last && *likes_comment_last == comment_col;
                   ++likes_comment_last, ++likes_user_last, ++likes_count);

            GrB_Matrix friends_subgraph;
            ok(GrB_Matrix_new(&friends_subgraph, GrB_BOOL, likes_count, likes_count));
            ok(GrB_Matrix_extract(friends_subgraph, GrB_NULL, GrB_NULL,
                                  input.friends_matrix, likes_user_first, likes_count, likes_user_first, likes_count,
                                  GrB_NULL));

            WriteOutDebugMatrix(std::to_string(comment_col).c_str(), friends_subgraph);

            likes_comment_first = likes_comment_last;
            likes_user_first = likes_user_last;

            ok(GrB_Matrix_free(&friends_subgraph));
        }
    }

    // TODO: replace sort by find k largest

    // Cleanup
    input.free();
    ok(GrB_finalize());
}
