package llvm4j.module.value;

import llvm4j.compile.StringCompiler;
import llvm4j.module.TypeValuePair;
import llvm4j.module.type.Type;

import java.util.Arrays;
import java.util.List;

public sealed interface Constant<V extends Constant<V>> extends Value {
    /// The two strings `true` and `false` are both valid constants of the i1 type.
    /// @param value The boolean value to represent, `true` or `false`.
    record Boolean(boolean value) implements Constant<Boolean> {
        @Override
        public void compile(StringCompiler stringBuilder) {
            stringBuilder.append(this.value ? "true" : "false");
        }
    }

    /// Standard integers (such as `4`) are constants of the integer type.
    /// @param value The value of the integer to encode.
    record Integer(long value) implements Constant<Integer> {
        @Override
        public void compile(StringCompiler stringBuilder) {
            stringBuilder.append(this.value);
        }
    }

    /// Floating-point constants must have a floating-point type.
    /// @param value The value of the floating-point to encode.
    record Float(double value) implements Constant<Float> {
        @Override
        public void compile(StringCompiler stringBuilder) {
            stringBuilder.append(this.value);
        }
    }

    /// The identifier `null` is recognized as a null pointer constant and must be of {@link Type.Pointer} type.
    record Null() implements Constant<Null> {
        @Override
        public void compile(StringCompiler stringBuilder) {
            stringBuilder.append("null");
        }
    }

    /// The string `undef` can be used anywhere a constant is expected and indicates that the user of the value
    /// may receive an unspecified bit-pattern. Undefined values may be of any type (other than `label` or `void``)
    /// and be used anywhere a constant is permitted.
    record Undef() implements Constant<Undef> {
        @Override
        public void compile(StringCompiler stringBuilder) {
            stringBuilder.append("undef");
        }
    }

    /// A poison value is a result of an erroneous operation.
    ///
    /// To facilitate speculative execution, many instructions do not invoke immediate undefined behavior
    /// when provided with illegal operands and return a poison value instead. The string `poison` can be used
    /// anywhere a constant is expected, and operations such as add with the nsw flag can produce a poison value.
    ///
    /// Most instructions return `poison` when one of their arguments is `poison`. A notable exception is the select
    /// instruction. Propagation of poison can be stopped with the freeze instruction.
    /// It is correct to replace a poison value with an undef value or any value of the type.
    ///
    /// This means that immediate undefined behavior occurs if a poison value is used as an instruction operand
    /// that has any values that trigger undefined behavior. Notably this includes (but is not limited to):
    ///
    /// - The pointer operand of a `load`, `store` or any other pointer dereferencing instruction (independent of address space).
    /// - The divisor operand of a `udiv`, `sdiv`, `urem` or srem instruction.
    /// - The condition operand of a `br` instruction.
    /// - The callee operand of a `call` or `invoke` instruction.
    /// - The parameter operand of a `call` or `invoke` instruction, when the function or invoking call site has a `noundef` attribute in the corresponding position.
    /// - The operand of a `ret` instruction if the function or invoking call site has a `noundef` attribute in the return value position.
    record Poison() implements Constant<Poison> {
        @Override
        public void compile(StringCompiler stringBuilder) {
            stringBuilder.append("poison");
        }
    }

    /// The identifier `none` is recognized as an empty token constant and must be of {@link Type.Token} type.
    record None() implements Constant<None> {
        @Override
        public void compile(StringCompiler stringBuilder) {
            stringBuilder.append("none");
        }
    }

    /// Structure constants are represented with notation similar to structure type definitions (a comma-separated
    /// list of elements, surrounded by braces (`{}`)). Structure constants must have {@link Type.Structure}
    /// type, and the number and types of elements must match those specified by the type.
    /// @param value
    record Structure(List<TypeValuePair> value) implements Constant<Structure> {
        @Override
        public void compile(StringCompiler stringBuilder) {
            stringBuilder.append("{")
                    .append(this.value, ", ")
                    .append('}');
        }
    }

    /// Array constants are represented with notation similar to array type definitions (a comma-separated list of
    /// elements, surrounded by square brackets (`[]`)). Array constants must have {@link Type.Array}
    /// type, and the number and types of elements must match those specified by the type.
    /// @param value
    record Array(List<TypeValuePair> value) implements Constant<Array> {
        @Override
        public void compile(StringCompiler stringBuilder) {
            stringBuilder.append("[")
                    .append(this.value, ", ")
                    .append(']');
        }
    }

    /// Vector constants are represented with notation similar to vector type definitions (a comma-separated list
    /// of elements, surrounded by less-than/greater-than’s (`<>`)). Vector constants must have {@link Type.Vector}
    /// type, and the number and types of elements must match those specified by the type.
    /// @param value
    record Vector(List<TypeValuePair> value) implements Constant<Vector> {
        @Override
        public void compile(StringCompiler stringBuilder) {
            stringBuilder.append("<")
                    .append(this.value, ", ")
                    .append('>');
        }
    }

    /// A metadata node is a constant tuple without types. Metadata can reference constant values as well.
    /// Unlike other typed constants that are meant to be interpreted as part of the instruction stream,
    /// metadata is a place to attach additional information such as debug info.
    /// @param wrapped The value to wrap as a metadata node.
    record Metadata<E extends Value>(Value wrapped) implements Constant<Metadata<E>> {
        @Override
        public void compile(StringCompiler stringBuilder) {
            stringBuilder.append('!')
                    .append(wrapped);
        }
    }

    static Constant.Integer integer(long value) {
        return new Constant.Integer(value);
    }

    static Constant.Float floating(double value) {
        return new Constant.Float(value);
    }

    static Constant.Boolean bool(boolean value) {
        return new Constant.Boolean(value);
    }

    static Constant.Null null_() {
        return new Constant.Null();
    }

    static Constant.Undef undef() {
        return new Constant.Undef();
    }

    static Constant.Poison poison() {
        return new Constant.Poison();
    }

    static Constant.None none() {
        return new Constant.None();
    }

    static Constant.Array array(List<TypeValuePair> values) {
        return new Constant.Array(values);
    }

    static Constant.Array array(Type type, Value... values) {
        return new Constant.Array(Arrays.stream(values)
                .map(x -> x.pair(type))
                .toList());
    }

    static Constant.Vector vector(List<TypeValuePair> values) {
        return new Constant.Vector(values);
    }

    static Constant.Vector vector(TypeValuePair... values) {
        return new Constant.Vector(Arrays.asList(values));
    }

    static Constant.Structure struct(List<TypeValuePair> values) {
        return new Constant.Structure(values);
    }

    static Constant.Structure struct(TypeValuePair... values) {
        return new Constant.Structure(Arrays.asList(values));
    }
}
