#pragma once

#include <memory>
#include <numeric>
#include <type_traits>

extern "C" {
#include <GraphBLAS.h>
#define LAGRAPH_EXPERIMENTAL_ASK_BEFORE_BENCHMARKING
#include <LAGraph.h>
}

//------------------------------------------------------------------------------
// ok: call a GraphBLAS method and check the result
//------------------------------------------------------------------------------
/// Throw exception on GraphBLAS error
inline __attribute__((always_inline))
GrB_Info ok(GrB_Info info, bool no_value_is_error = true) {
    using namespace std::string_literals;

    if (info == GrB_SUCCESS || (!no_value_is_error && info == GrB_NO_VALUE)) {
        return info;
    } else {
        throw std::runtime_error{"GraphBLAS error: "s + std::to_string(info)};
    }
}

/// Throw exception on LAGraph error
inline __attribute__((always_inline))
int ok(int status) {
    using namespace std::string_literals;

    if (status < 0) {
        throw std::runtime_error{"LAGraph error: "s + std::to_string(status)};
    } else {
        return status;
    }
}

inline __attribute__((always_inline))
std::unique_ptr<bool[]> array_of_true(size_t n) {
    std::unique_ptr<bool[]> array{new bool[n]};

    int nthreads;
    ok(LAGraph_GetNumThreads(&nthreads, nullptr));
    nthreads = std::min<size_t>(n / 4096, nthreads);
    nthreads = std::max(nthreads, 1);
#pragma omp parallel for num_threads(nthreads) schedule(static)
    for (size_t i = 0; i < n; ++i) {
        array[i] = true;
    }

    return array;
}

inline __attribute__((always_inline))
std::unique_ptr<GrB_Index[]> array_of_indices(size_t n) {
    std::unique_ptr<GrB_Index[]> array{new GrB_Index[n]};

    int nthreads;
    ok(LAGraph_GetNumThreads(&nthreads, nullptr));
    nthreads = std::min<size_t>(n / 4096, nthreads);
    nthreads = std::max(nthreads, 1);
#pragma omp parallel for num_threads(nthreads) schedule(static)
    for (size_t i = 0; i < n; ++i) {
        array[i] = i;
    }

    return array;
}

/*
 * DEBUG FUNCTIONS
 */

inline void WriteOutDebugMatrix(GrB_Matrix result, const char *title = nullptr) {
    if (title)
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
                printf("GraphBLAS error");
            }

        }
        printf("\n");
    }
}

inline void WriteOutDebugVector(GrB_Vector result, const char *title = nullptr) {
    if (title)
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
            throw std::runtime_error{"GraphBLAS error."};
        }

    }
    printf("\n");
}

namespace gbxx {
    inline void GBxx_free_cpp(GrB_Type *pointer) { ok(GrB_Type_free(pointer)); }

    inline void GBxx_free_cpp(GrB_UnaryOp *pointer) { ok(GrB_UnaryOp_free(pointer)); }

    inline void GBxx_free_cpp(GrB_BinaryOp *pointer) { ok(GrB_BinaryOp_free(pointer)); }

    inline void GBxx_free_cpp(GxB_SelectOp *pointer) { ok(GxB_SelectOp_free(pointer)); }

    inline void GBxx_free_cpp(GrB_Monoid *pointer) { ok(GrB_Monoid_free(pointer)); }

    inline void GBxx_free_cpp(GrB_Semiring *pointer) { ok(GrB_Semiring_free(pointer)); }

    inline void GBxx_free_cpp(GxB_Scalar *pointer) { ok(GxB_Scalar_free(pointer)); }

    inline void GBxx_free_cpp(GrB_Vector *pointer) { ok(GrB_Vector_free(pointer)); }

    inline void GBxx_free_cpp(GrB_Matrix *pointer) { ok(GrB_Matrix_free(pointer)); }

    inline void GBxx_free_cpp(GrB_Descriptor *pointer) { ok(GrB_Descriptor_free(pointer)); }

    inline void GBxx_free_cpp(LAGraph_Graph *pointer) { ok(LAGraph_Delete(pointer, nullptr)); }

    template<typename Type>
    struct GBxx_deleter {
        void operator()(Type b) {
            GBxx_free_cpp(&b);
        }
    };
}

/**
 * Owning smart pointer that disposes GrB object when it goes out of scope.
 *
 * Usage:
 * Initialize using first (out) parameter of function
 *   GBxx_Object<GrB_Matrix> mx = GB(GrB_Matrix_new, GrB_BOOL, nrows, ncols);
 *
 * Initialize manually
 *   GrB_Matrix mx_ptr = nullptr;
 *   // init
 *   GBxx_Object<GrB_Matrix> mx{mx_ptr};
 *
 * When a GrB_Matrix* pointer is needed
 *   // binwrite might change the pointer since it uses export and import
 *   GrB_Matrix mx_ptr = mx.release();
 *   LAGraph_binwrite(&mx_ptr, "outfile.grb", nullptr);
 *   mx.reset(mx_ptr);
 */
template<typename Type>
using GBxx_Object = std::unique_ptr<typename std::remove_pointer<Type>::type, gbxx::GBxx_deleter<Type>>;

template<typename Type>
using GBxx_Object_shared = std::shared_ptr<typename std::remove_pointer<Type>::type>;

/**
 * Initialize a GBxx_Object<Type> smart pointer using func.
 *
 * Usage:
 *   GBxx_Object<GrB_Matrix> mx = GB(GrB_Matrix_new, GrB_BOOL, nrows, ncols);
 *
 * @tparam Type GrB object type
 * @tparam ReturnType return type of func
 * @param func A function: func(Type*, Args...)
 * @param args 2nd and more parameters of func.
 * @return The smart pointer initialized with func(&out, args...)
 */
template<typename Type, typename ReturnType, typename ...ArgsIn, typename ...Args>
GBxx_Object<Type> GB(ReturnType (&func)(Type *, Args...), ArgsIn &&... args) {
    Type gb_instance = nullptr;
    ok(func(&gb_instance, std::forward<ArgsIn>(args)...));

    return {gb_instance, {}};
}

template<typename Z, typename X>
using GBxx_unary_function = void (*)(Z *, const X *);

/**
 * unwrap(GBxx_Object<GrB_*> o) = o.get()
 * unwrap(GrB_* o) = o
 */
template<typename Type>
auto unwrap(Type const &v) {
    if constexpr (std::is_pointer_v<Type>)
        return v;
    else
        return v.get();
}

//
// Wrappers
//

template<typename GBxx_Object>
GrB_Index GBxx_nvals(GBxx_Object const &o) {
    auto ptr = unwrap(o);
    GrB_Index nvals;
    if constexpr (std::is_same_v<decltype(ptr), GrB_Matrix>)
        ok(GrB_Matrix_nvals(&nvals, ptr));
    else if constexpr (std::is_same_v<decltype(ptr), GrB_Vector>)
        ok(GrB_Vector_nvals(&nvals, ptr));
    else
        ok(GxB_Scalar_nvals(&nvals, ptr));
    return nvals;
}

inline GBxx_Object<LAGraph_Graph> to_LAGraph(GBxx_Object<GrB_Matrix> mx, LAGraph_Kind kind = LAGRAPH_KIND_UNKNOWN) {
    GrB_Matrix mx_ptr = mx.release();
    return GB(LAGraph_New, &mx_ptr, kind, nullptr);
}
