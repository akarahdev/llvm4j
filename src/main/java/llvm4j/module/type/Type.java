package llvm4j.module.type;

import llvm4j.compile.Compilable;
import llvm4j.module.value.*;

import java.util.List;

public interface Type extends Compilable {
    /// The `void` type does not represent any value and has no size.
    static Type voidType() {
        return sc -> sc.append("void");
    }

    /// Opaque structure types are used to represent structure types that do not have a body specified.
    /// This corresponds (for example) to the C notion of a forward declared structure.
    /// They can be named (`%X`) or unnamed (`%52`).
    ///
    /// It is not possible to create SSA values with an opaque structure type.
    /// In practice, this largely limits their use to the value type of external globals.
    static Type opaque() {
        return sc -> sc.append("opaque");
    }

    /// The `label` type represents code labels.
    static Type label() {
        return sc -> sc.append("label");
    }

    /// The `token` type is used when a value is associated with an instruction, but all uses
    /// of the value must not attempt to introspect or obscure it.
    ///
    /// As such, it is not appropriate to have a `phi` or `select` of type `token`.
    static Type token() {
        return sc -> sc.append("token");
    }

    /// The `metadata` type represents embedded metadata.
    ///
    /// No derived types may be created from metadata except for function arguments.
    static Type metadata() {
        return sc -> sc.append("metadata");
    }

    /// The function type can be thought of as a function signature.
    /// It consists of a return type and a list of formal parameter types.
    /// The return type of the function type is a void type or first-class type — except for label and metadata types.
    ///
    /// @param returned The return type of the function type.
    /// @param inputs The parameter types of the function type.
    static Type function(Type returned, List<? extends Type> inputs) {
        return sc -> sc.append(returned).append('(').append(inputs, ", ").append(')');
    }

    static Type function(Type returned, List<? extends Type> inputs, boolean variadic) {
        return sc -> sc.append(returned).append('(').append(inputs, ", ")
                .appendIf(() -> variadic, sc2 -> sc2.append(", ...")).append(')');
    }

    static Type named(Identifier identifier) {
        return sc -> sc.append(identifier);
    }

    /// The integer type is a very simple type that simply specifies an arbitrary bit width
    /// for the integer type desired. Any bit width from 1 bit to 2^23 (about 8 million)
    /// can be specified.
    ///
    /// @param bits The bits of the integer to use.
    static Type integer(int bits) {
        return sc -> sc.append('i').append(bits);
    }

    /// A vector type is a simple derived type that represents a vector of elements.
    ///
    /// Vector types are used when multiple primitive data are operated in parallel
    /// using a single instruction (SIMD). A vector type requires a size (number of elements),
    /// an underlying primitive data type, and a scalable property to represent
    /// vectors where the exact hardware vector length is unknown at compile time.
    ///
    /// @param inner The inner type of the vector.
    /// @param size The size of the vector.
    static Type vector(int size, Type inner) {
        return sc -> sc.append("<{} x {}>", size, inner);
    }

    /// The array type is a very simple derived type that arranges elements sequentially in memory.
    /// The array type requires a size (number of elements) and an underlying data type.
    /// @param inner The element type of the array.
    /// @param size The size of the array.
    static Type array(int size, Type inner) {
        return sc -> sc.append("[{} x {}]", size, inner);
    }

    /// The structure type is used to represent a collection of data members
    /// together in memory. The elements of a structure may be any type that has a size.
    /// @param types The members of the structure type.
    static Type struct(List<Type> types) {
        return sc -> sc.append('{').append(types, ", ").append('}');
    }

    /// Variant of the {@link Structure} type, where all members are packed
    /// in memory.
    ///
    /// Structures may optionally be “packed” structures, which indicate that the alignment
    /// of the struct is one byte and that there is no padding between the elements.
    /// In non-packed structs, padding between field types is inserted as defined by the
    /// DataLayout string in the module, which is required to match what the underlying
    /// code generator expects.
    ///
    /// @param types The members of the structure type.
    static Type packedStruct(List<Type> types) {
        return sc -> sc.append("<{").append(types, ", ").append("}>");
    }

    /// The pointer type ptr is used to specify memory locations.
    /// Pointers are commonly used to reference objects in memory.
    static Type ptr() {
        return sc -> sc.append("ptr");
    }

    default TypeValuePair pair(Value value) {
        return new TypeValuePair(this, value);
    }

    default TypeConstantPair constantTyped(Constant other) {
        return new TypeConstantPair(this, other);
    }

    default TypeIdentifierPair idTyped(Identifier other) {
        return new TypeIdentifierPair(this, other);
    }
}
