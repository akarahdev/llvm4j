package llvm4j.type;

import llvm4j.compile.Compilable;
import llvm4j.compile.StringCompiler;
import llvm4j.value.Identifier;

import java.util.List;

public sealed interface Type extends Compilable {
    /**
     * The void type does not represent any value and has no size.
     */
    record Void() implements Type {
        @Override
        public void compile(StringCompiler stringBuilder) {
            stringBuilder.append("void");
        }
    }

    /**
     * Opaque structure types are used to represent structure types that do not have a body specified.
     * This corresponds (for example) to the C notion of a forward declared structure.
     * They can be named (%X) or unnamed (%52).
     * It is not possible to create SSA values with an opaque structure type.
     * In practice, this largely limits their use to the value type of external globals.
     */
    record Opaque() implements Type {
        @Override
        public void compile(StringCompiler stringBuilder) {
            stringBuilder.append("opaque");
        }
    }

    /**
     * The label type represents code labels.
     */
    record Label() implements Type {
        @Override
        public void compile(StringCompiler stringBuilder) {
            stringBuilder.append("label");
        }
    }

    /**
     * The token type is used when a value is associated with an instruction but all uses
     * of the value must not attempt to introspect or obscure it.
     * As such, it is not appropriate to have a phi or select of type token.
     */
    record Token() implements Type {
        @Override
        public void compile(StringCompiler stringBuilder) {
            stringBuilder.append("token");
        }
    }

    /**
     * The metadata type represents embedded metadata.
     * No derived types may be created from metadata except for function arguments.
     */
    record Metadata() implements Type {
        @Override
        public void compile(StringCompiler stringBuilder) {
            stringBuilder.append("metadata");
        }
    }

    /**
     * The function type can be thought of as a function signature.
     * It consists of a return type and a list of formal parameter types.
     * The return type of function type is a void type or first class type — except for label and metadata types.
     * @param returned The return type of the function type.
     * @param inputs The parameter types of the function type.
     */
    record Function(Type returned, List<Type> inputs) implements Type {
        @Override
        public void compile(StringCompiler stringBuilder) {
            stringBuilder.append(returned.toString())
                    .append('(')
                    .append(inputs, ", ")
                    .append(')');
        }
    }

    record Named(Identifier identifier) implements Type {
        @Override
        public void compile(StringCompiler stringBuilder) {
            stringBuilder.append(identifier);
        }
    }

    /**
     * The integer type is a very simple type that simply specifies an arbitrary bit width
     * for the integer type desired. Any bit width from 1 bit to 2^23 (about 8 million)
     * can be specified.
     * @param bits The bits of the integer to use.
     */
    record Integer(int bits) implements Type {
        @Override
        public void compile(StringCompiler stringBuilder) {
            stringBuilder.append('i').append(this.bits);
        }
    }

    /**
     * A vector type is a simple derived type that represents a vector of elements.
     * Vector types are used when multiple primitive data are operated in parallel
     * using a single instruction (SIMD). A vector type requires a size (number of elements),
     * an underlying primitive data type, and a scalable property to represent
     * vectors where the exact hardware vector length is unknown at compile time.
     * @param inner The inner type of the vector.
     * @param size The size of the vector.
     */
    record Vector(Type inner, int size) implements Type {
        @Override
        public void compile(StringCompiler stringBuilder) {
            stringBuilder.append('<')
                    .append(inner)
                    .append(" x ")
                    .append(size)
                    .append('>');
        }
    }

    /**
     * The array type is a very simple derived type that arranges elements sequentially in memory. The array type requires a size (number of elements) and an underlying data type.
     * @param inner The element type of the array.
     * @param size The size of the array.
     */
    record Array(Type inner, int size) implements Type {
        @Override
        public void compile(StringCompiler stringBuilder) {
            stringBuilder.append('[')
                    .append(inner)
                    .append(" x ")
                    .append(size)
                    .append(']');
        }
    }

    /**
     * The structure type is used to represent a collection of data members
     * together in memory. The elements of a structure may be any type that has a size.
     * @param types The members of the structure type.
     */
    record Structure(List<Type> types) implements Type {
        @Override
        public void compile(StringCompiler stringBuilder) {
            stringBuilder.append('{')
                    .append(this.types, ", ")
                    .append('}');
        }
    }

    /**
     * Variant of the {@link Structure} type, where all members are packed
     * in memory.
     * Structures may optionally be “packed” structures, which indicate that the alignment
     * of the struct is one byte, and that there is no padding between the elements.
     * In non-packed structs, padding between field types is inserted as defined by the
     * DataLayout string in the module, which is required to match what the underlying
     * code generator expects.
     *
     * @param types The members of the structure type.
     */
    record PackedStructure(List<Type> types) implements Type {
        @Override
        public void compile(StringCompiler stringBuilder) {
            stringBuilder.append("<{")
                    .append(this.types, ", ")
                    .append("}>");
        }
    }

    /**
     * The pointer type ptr is used to specify memory locations.
     * Pointers are commonly used to reference objects in memory.
     */
    record Pointer() implements Type {
        @Override
        public void compile(StringCompiler stringBuilder) {
            stringBuilder.append("ptr");
        }
    }
}
