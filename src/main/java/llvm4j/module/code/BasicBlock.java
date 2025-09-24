package llvm4j.module.code;

import llvm4j.compile.Compilable;
import llvm4j.compile.StringCompiler;
import llvm4j.module.TypeValuePair;
import llvm4j.module.type.Type;
import llvm4j.module.value.Constant;
import llvm4j.module.value.Identifier;
import llvm4j.module.value.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record BasicBlock(
        String identifier,
        List<Instruction> instructions,
        List<BasicBlock> children
) implements Compilable {

    @Override
    public void compile(StringCompiler stringBuilder) {
        stringBuilder
                .append("\n  ")
                .append(this.identifier)
                .append(": ")
                .append(this.instructions, " ");
        stringBuilder.append(this.children, "");
    }

    public static class Builder {
        String identifier;
        List<Instruction> instructions = new ArrayList<>();
        FunctionBody.Builder functionBuilder;
        List<BasicBlock> children = new ArrayList<>();

        protected Builder(FunctionBody.Builder functionBuilder) {
            this.identifier = "$" + Integer.toHexString(System.identityHashCode(this));
            this.functionBuilder = functionBuilder;
        }

        public BasicBlock.Builder retVoid() {
            this.instructions.add(stringBuilder ->
                    stringBuilder.append("\n    ")
                            .append("ret void"));
            return this;
        }

        public BasicBlock.Builder ret(TypeValuePair<?, ?> typeValuePair) {
            this.instructions.add(stringBuilder ->
                    stringBuilder.append("\n    ")
                            .append("ret ")
                            .append(typeValuePair.type())
                            .append(' ')
                            .append(typeValuePair.value()));
            return this;
        }

        public BasicBlock.Builder branch(Value<?> condition, BuildMapper then, BuildMapper orElse) {
            var bb1 = then.apply(new BasicBlock.Builder(this.functionBuilder)).build();
            var bb2 = orElse.apply(new BasicBlock.Builder(this.functionBuilder)).build();
            this.children.add(bb1);
            this.children.add(bb2);
            this.instructions.add(stringBuilder ->
                    stringBuilder.append("\n    ")
                            .append("br i1 ")
                            .append(condition)
                            .append(", label %")
                            .append(bb1.identifier())
                            .append(", label %")
                            .append(bb2.identifier())
            );
            return this;
        }

        public Value<?> add(TypeValuePair<?, ?> lhs, TypeValuePair<?, ?> rhs) {
            assert lhs.type().equals(rhs.type());
            var id = new Identifier.Local("$" + System.identityHashCode(new Object()));
            this.instructions.add(stringBuilder ->
                    stringBuilder
                            .append("\n    ")
                            .append(id)
                            .append(" = add ")
                            .append(lhs)
                            .append(", ")
                            .append(rhs.value()));
            return id;
        }

        public Value<?> sub(TypeValuePair<?, ?> lhs, TypeValuePair<?, ?> rhs) {
            assert lhs.type().equals(rhs.type());
            var id = new Identifier.Local("$" + System.identityHashCode(new Object()));
            this.instructions.add(stringBuilder ->
                    stringBuilder
                            .append("\n    ")
                            .append(id)
                            .append(" = sub ")
                            .append(lhs)
                            .append(", ")
                            .append(rhs.value()));
            return id;
        }

        public Constant.Integer integer(long value) {
            return new Constant.Integer(value);
        }

        public Constant.Boolean bool(boolean value) {
            return new Constant.Boolean(value);
        }

        public Constant.Array array(List<TypeValuePair<?, ?>> values) {
            return new Constant.Array(values);
        }

        public Constant.Vector vector(List<TypeValuePair<?, ?>> values) {
            return new Constant.Vector(values);
        }

        public Constant.Structure struct(List<TypeValuePair<?, ?>> values) {
            return new Constant.Structure(values);
        }

        protected BasicBlock build() {
            return new BasicBlock(
                    this.identifier,
                    Collections.unmodifiableList(this.instructions),
                    this.children
            );
        }
    }

    @FunctionalInterface
    public interface BuildMapper {
        BasicBlock.Builder apply(BasicBlock.Builder builder);
    }
}
