#pragma once

extern "C" {
#include <GraphBLAS.h>
}

#include "utils.h"

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

template<typename Type, typename ...Args>
GrB_Object_cpp<Type> GB(GrB_Info (&func)(Type *, Args...), Args ...args) {
    Type gb_instance = nullptr;
    ok(func(&gb_instance, args...));

    return {gb_instance, {}};
}
