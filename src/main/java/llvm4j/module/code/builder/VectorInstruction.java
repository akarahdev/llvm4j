package llvm4j.module.code.builder;

import llvm4j.module.TypeValuePair;
import llvm4j.module.type.Type;
import llvm4j.module.value.Identifier;
import llvm4j.module.value.Value;

public interface VectorInstruction<T extends VectorInstruction<T>> extends BasicBlockBuilderHook {
    default Value extractElement(TypeValuePair vector, TypeValuePair index) {
        var id = Identifier.localRandom();
        this.instructions().add(sc -> sc.append("{} = extractelement {}, {}", id, vector, index));
        return id;
    }

    default Value insertElement(TypeValuePair vector, TypeValuePair element, TypeValuePair index) {
        var id = Identifier.localRandom();
        this.instructions().add(sc -> sc.append("{} = insertelement {}, {}", id, vector, element, index));
        return id;
    }

    /// # Overview
    /// The `shufflevector` instruction constructs a permutation of elements from two input vectors,
    /// returning a vector with the same element type as the input and length that is the same
    /// as the shuffle mask.
    ///
    /// # Arguments
    /// The first two operands of a `shufflevector` instruction are vectors with the same type.
    /// The third argument is a shuffle mask vector constant whose element type is `i32`.
    /// The mask vector elements must be constant integers or poison values.
    /// The result of the instruction is a vector whose length is the same as the shuffle
    /// mask and whose element type is the same as the element type of the first two operands.
    ///
    /// # Semantics
    /// The elements of the two input vectors are numbered from left to right across both of the vectors.
    /// For each element of the result vector, the shuffle mask selects an element from one of the input
    /// vectors to copy to the result. Non-negative elements in the mask represent an index into the
    /// concatenated pair of input vectors.
    ///
    /// A poison element in the mask vector specifies that the resulting element is poison. For backwards-compatibility
    /// reasons, LLVM temporarily also accepts undef mask elements, which will be interpreted the same way as poison
    /// elements. If the shuffle mask selects an undef element from one of the input vectors, the resulting element
    /// is undef.
    ///
    /// For scalable vectors, the only valid mask values at present are zeroinitializer, undef and poison,
    /// since we cannot write all indices as literals for a vector with a length unknown at compile time.
    default Value shuffleVector(TypeValuePair vector, TypeValuePair other, TypeValuePair mask) {
        var id = Identifier.localRandom();
        this.instructions().add(sc -> sc.append("{} = shufflevector {}, {}, {}", id, vector, other, mask));
        return id;
    }
}
