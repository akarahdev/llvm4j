package llvm4j.module.code;

import llvm4j.compile.Compilable;
import llvm4j.compile.StringCompiler;
import llvm4j.module.TypeValuePair;
import llvm4j.module.code.builder.*;
import llvm4j.module.type.Type;
import llvm4j.module.value.Constant;
import llvm4j.module.value.Identifier;
import llvm4j.module.value.Value;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record BasicBlock(
        Identifier.Local identifier,
        List<Instruction> instructions,
        List<BasicBlock> children
) implements Compilable {

    @Override
    public void compile(StringCompiler stringBuilder) {
        stringBuilder
                .append("\n  ")
                .append(this.identifier.identifier())
                .append(": ")
                .append("\n    ")
                .append(this.instructions, "\n    ");
        stringBuilder.append(this.children, "");
    }

    public static class Builder implements
            BasicBlockBuilderHook, ArithmeticInstructionHook<Builder>, ControlFlowInstructionHook<Builder>,
            AggregateInstructions<Builder>, VectorInstruction<Builder> {
        Identifier.Local identifier;
        List<Instruction> instructions = new ArrayList<>();
        FunctionBody.Builder functionBuilder;
        List<BasicBlock> children = new ArrayList<>();

        public Builder(FunctionBody.Builder functionBuilder) {
            this.identifier = Identifier.localRandom();
            this.functionBuilder = functionBuilder;
        }

        public BasicBlock build() {
            return new BasicBlock(
                    this.identifier,
                    Collections.unmodifiableList(this.instructions),
                    this.children
            );
        }

        @Override
        public Identifier.Local identifier() {
            return this.identifier;
        }

        @Override
        public List<Instruction> instructions() {
            return this.instructions;
        }

        @Override
        public FunctionBody.Builder functionBuilder() {
            return this.functionBuilder;
        }

        @Override
        public List<BasicBlock> children() {
            return this.children;
        }
    }

    @FunctionalInterface
    public interface BuildMapper {
        BasicBlock.Builder apply(BasicBlock.Builder builder);
    }
}
