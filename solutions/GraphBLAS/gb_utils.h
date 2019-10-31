#pragma once

extern "C" {
#include <GraphBLAS.h>
}

//------------------------------------------------------------------------------
// ok: call a GraphBLAS method and check the result
//------------------------------------------------------------------------------

// ok(GrB_Info) is a function that processes result of a GraphBLAS method and checks the status;
// if a failure occurs, it returns the error status to the caller.

inline __attribute__((always_inline))
GrB_Info ok(GrB_Info info, bool no_value_is_error = true) {
    if (info == GrB_SUCCESS || (!no_value_is_error && info == GrB_NO_VALUE))
        return info;
    else
        throw std::runtime_error{std::string{"GraphBLAS error: "} + GrB_error()};
}

inline __attribute__((always_inline))
std::unique_ptr<bool[]> array_of_true(size_t n){
    std::unique_ptr<bool[]> array{new bool[n]};
    std::fill_n(array.get(), n, true);

    return array;
}

/*
 * DEBUG FUNCTIONS
 */

inline void WriteOutDebugMatrix(const char *title, GrB_Matrix result) {
    printf("%s:\n", title);
    GrB_Index rows, cols;
    ok(GrB_Matrix_nrows(&rows, result));
    ok(GrB_Matrix_ncols(&cols, result));
    double element;

    for (GrB_Index i = 0; i < rows; i++) {
        for (GrB_Index j = 0; j < cols; j++) {
            GrB_Info info = ok(GrB_Matrix_extractElement_FP64(&element, result, i, j), false);

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

inline void WriteOutDebugVector(const char *title, GrB_Vector result) {
    printf("%s:\n", title);
    GrB_Index size;
    ok(GrB_Vector_size(&size, result));
    double element;

    for (unsigned int i = 0; i < size; i++) {
        GrB_Info info = ok(GrB_Vector_extractElement_FP64(&element, result, i), false);

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

namespace gb_cpp {
    inline void GB_free_cpp(GrB_Type *pointer) { ok(GrB_Type_free(pointer)); }

    inline void GB_free_cpp(GrB_UnaryOp *pointer) { ok(GrB_UnaryOp_free(pointer)); }

    inline void GB_free_cpp(GrB_BinaryOp *pointer) { ok(GrB_BinaryOp_free(pointer)); }

    inline void GB_free_cpp(GxB_SelectOp *pointer) { ok(GxB_SelectOp_free(pointer)); }

    inline void GB_free_cpp(GrB_Monoid *pointer) { ok(GrB_Monoid_free(pointer)); }

    inline void GB_free_cpp(GrB_Semiring *pointer) { ok(GrB_Semiring_free(pointer)); }

    inline void GB_free_cpp(GxB_Scalar *pointer) { ok(GxB_Scalar_free(pointer)); }

    inline void GB_free_cpp(GrB_Vector *pointer) { ok(GrB_Vector_free(pointer)); }

    inline void GB_free_cpp(GrB_Matrix *pointer) { ok(GrB_Matrix_free(pointer)); }

    inline void GB_free_cpp(GrB_Descriptor *pointer) { ok(GrB_Descriptor_free(pointer)); }

    template<typename Type>
    struct GrB_cpp_deleter {
        void operator()(Type b) {
            GB_free_cpp(&b);
        }
    };
}

template<typename Type>
using GrB_Object_cpp = std::unique_ptr<typename std::remove_pointer<Type>::type, gb_cpp::GrB_cpp_deleter<Type>>;

template<typename Type, typename ...Args, typename ...Args2>
GrB_Object_cpp<Type> GB(GrB_Info (&func)(Type *, Args2...), Args &&... args) {
    Type gb_instance = nullptr;
    ok(func(&gb_instance, std::forward<Args>(args)...));

    return {gb_instance, {}};
}

template<typename Z, typename X>
using GB_unary_function_cpp = void (*)(Z *, const X *);
