package llvm4j.module;

import llvm4j.compile.Compilable;
import llvm4j.compile.StringCompiler;
import llvm4j.module.code.FunctionBody;
import llvm4j.module.type.Type;
import llvm4j.module.value.Identifier;

import java.util.List;
import java.util.Optional;

public record Function(
        Identifier name,
        Type returnType,
        List<TypeValuePair> parameters,
        Optional<FunctionBody> functionBody
) implements Compilable {
    @Override
    public void compile(StringCompiler stringBuilder) {
        stringBuilder.append(functionBody.map(_ -> "define").orElse("declare"))
                .append(' ')
                .append(returnType)
                .append(' ')
                .append(name)
                .append('(')
                .append(this.parameters, ", ")
                .append(')')
                .append(' ')
                .append(this.functionBody);
    }

}
