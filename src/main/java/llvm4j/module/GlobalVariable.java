package llvm4j.module;

import llvm4j.compile.Compilable;
import llvm4j.compile.StringCompiler;
import llvm4j.module.value.*;

public record GlobalVariable(
    Identifier.Global name,
    TypeConstantPair value
) implements Compilable {
    @Override
    public void compile(StringCompiler stringBuilder) {
        stringBuilder.append("{} = global {}", name, value);
    }

    public static GlobalVariable create(
        Identifier.Global name,
        TypeConstantPair value
    ) {
        return new GlobalVariable(name, value);
    }
}
