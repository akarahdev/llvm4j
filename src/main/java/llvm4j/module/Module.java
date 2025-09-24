package llvm4j.module;

import llvm4j.compile.Compilable;
import llvm4j.compile.StringCompiler;
import llvm4j.module.value.Identifier;
import llvm4j.module.value.TypeConstantPair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public record Module(
        List<GlobalVariable> globalVariables,
        List<Function> functions
) implements Compilable {
    public static Builder builder() {
        return new Builder();
    }

    @Override
    public void compile(StringCompiler stringBuilder) {
        stringBuilder.append(this.globalVariables, "\n");
        stringBuilder.append(this.functions, "\n\n");
    }

    public void emit(Path target) throws IOException {
        Files.writeString(target, this.compile());
    }

    public static class Builder {
        List<Function> functions = new ArrayList<>();
        List<GlobalVariable> globalVariables = new ArrayList<>();

        public Builder withFunction(Identifier.Global name, java.util.function.Function<Function.Builder, Function.Builder> function) {
            this.functions.add(function.apply(Function.builder(name)).build());
            return this;
        }

        public Builder withGlobalVariable(Identifier.Global name, TypeConstantPair value) {
            this.globalVariables.add(new GlobalVariable(name, value));
            return this;
        }

        public Module build() {
            return new Module(this.globalVariables, this.functions);
        }
    }
}
