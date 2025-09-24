package llvm4j.module.code;

import llvm4j.compile.Compilable;
import llvm4j.compile.StringCompiler;

public record FunctionBody() implements Compilable {
    @Override
    public void compile(StringCompiler stringBuilder) {
        stringBuilder.append("{}");
    }
}
