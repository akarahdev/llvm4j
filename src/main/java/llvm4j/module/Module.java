package llvm4j.module;

import llvm4j.compile.Compilable;
import llvm4j.compile.StringCompiler;
import llvm4j.module.value.Identifier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public record Module(
        List<Function> functions
) implements Compilable {
    public static Builder builder() {
        return new Builder();
    }

    @Override
    public void compile(StringCompiler stringBuilder) {
        stringBuilder.append(this.functions, "\n\n");
    }

    public void emit(Path target) throws IOException {
        Files.writeString(target, this.compile());
    }

    public static class Builder {
        List<Function> functions = new ArrayList<>();

        public Builder withFunction(Identifier.Global name, java.util.function.Function<Function.Builder, Function.Builder> function) {
            this.functions.add(function.apply(Function.builder(name)).build());
            return this;
        }

        public Module build() {
            return new Module(this.functions);
        }
    }
}
