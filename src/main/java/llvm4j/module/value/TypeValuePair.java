package llvm4j.module.value;

import llvm4j.compile.Compilable;
import llvm4j.compile.StringCompiler;
import llvm4j.module.type.Type;

public record TypeValuePair(
        Type type,
        Value value
) implements Compilable {
    @Override
    public void compile(StringCompiler stringBuilder) {
        stringBuilder.append(type)
                .append(' ')
                .append(value);
    }
}
