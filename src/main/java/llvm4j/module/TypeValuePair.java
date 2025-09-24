package llvm4j.module;

import llvm4j.compile.Compilable;
import llvm4j.compile.StringCompiler;
import llvm4j.module.type.Type;
import llvm4j.module.value.Identifier;

public record TypeValuePair(
        Identifier name,
        Type type
) implements Compilable {
    @Override
    public void compile(StringCompiler stringBuilder) {
        stringBuilder.append(type)
                .append(' ')
                .append(name);
    }
}
