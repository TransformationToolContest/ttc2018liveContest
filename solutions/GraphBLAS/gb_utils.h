#pragma once

extern "C" {
#include <GraphBLAS.h>
}

#include "utils.h"

inline void GrB_free_cpp(GrB_Type *pointer) { ok(GrB_Type_free(pointer)); }

inline void GrB_free_cpp(GrB_UnaryOp *pointer) { ok(GrB_UnaryOp_free(pointer)); }

inline void GrB_free_cpp(GrB_BinaryOp *pointer) { ok(GrB_BinaryOp_free(pointer)); }

inline void GxB_free_cpp(GxB_SelectOp *pointer) { ok(GxB_SelectOp_free(pointer)); }

inline void GrB_free_cpp(GrB_Monoid *pointer) { ok(GrB_Monoid_free(pointer)); }

inline void GrB_free_cpp(GrB_Semiring *pointer) { ok(GrB_Semiring_free(pointer)); }

inline void GxB_free_cpp(GxB_Scalar *pointer) { ok(GxB_Scalar_free(pointer)); }

inline void GrB_free_cpp(GrB_Vector *pointer) { ok(GrB_Vector_free(pointer)); }

inline void GrB_free_cpp(GrB_Matrix *pointer) { ok(GrB_Matrix_free(pointer)); }

inline void GrB_free_cpp(GrB_Descriptor *pointer) { ok(GrB_Descriptor_free(pointer)); }
