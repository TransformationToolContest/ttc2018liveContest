#include <string>
#include <chrono>

#include "utils.h"

time_t GetCurrentMilliseconds() {
    using namespace std::chrono;
    return duration_cast<milliseconds>(
        system_clock::now().time_since_epoch()
    ).count();
}

/*
 * DEBUG FUNCTIONS
 */

void WriteOutDebugMatrix(const char *title, GrB_Matrix result) {
    printf("%s:\n", title);
    GrB_Index rows, cols;
    GrB_Matrix_nrows(&rows, result);
    GrB_Matrix_ncols(&cols, result);
    double element;

    for (GrB_Index i = 0; i < rows; i++) {
        for (GrB_Index j = 0; j < cols; j++) {
            GrB_Info info = GrB_Matrix_extractElement_FP64(&element, result, i, j);

            if (info == GrB_SUCCESS) {
                printf("%g ", element);
            } else if (info == GrB_NO_VALUE) {
                // It is up to the user to determine what 'no value'
                // means.  It depends on the semiring used.
                printf("- ");
            } else {
                printf("Error! %s\n", GrB_error());
            }

        }
        printf("\n");
    }
}

void WriteOutDebugVector(const char *title, GrB_Vector result) {
    printf("%s:\n", title);
    GrB_Index size;
    GrB_Vector_size(&size, result);
    double element;

    for (unsigned int i = 0; i < size; i++) {
        GrB_Info info = GrB_Vector_extractElement_FP64(&element, result, i);

        if (info == GrB_SUCCESS) {
            if (element == UINT64_MAX) {
                printf("inf ");
            } else {
                printf("%g ", element);
            }

        } else if (info == GrB_NO_VALUE) {
            // It is up to the user to determine what 'no value'
            // means.  It depends on the semiring used.
            printf("- ");
        } else {
            printf("Error! %s\n", GrB_error());
        }

    }
    printf("\n");
}
