package llvm4j.module;

import llvm4j.compile.Compilable;
import llvm4j.compile.StringCompiler;
import llvm4j.module.code.FunctionBody;
import llvm4j.module.type.Type;
import llvm4j.module.value.Identifier;
import llvm4j.module.value.Value;

import javax.swing.plaf.ButtonUI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record Function(
        Identifier.Global name,
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

    public static Builder builder(Identifier.Global name) {
        return new Builder(name);
    }

    public static class Builder {
        Identifier.Global name;
        Type returnType = Type.voidType();
        List<TypeValuePair> parameters = new ArrayList<>();
        Optional<FunctionBody> functionBody = Optional.empty();

        private Builder(Identifier.Global name) {
            this.name = name;
        }

        public Builder withReturnType(Type type) {
            this.returnType = type;
            return this;
        }

        public Builder withParameter(TypeValuePair parameter) {
            this.parameters.add(parameter);
            return this;
        }

        public Builder withBody(FunctionBody body) {
            this.functionBody = Optional.of(body);
            return this;
        }

        public Function build() {
            return new Function(
                    this.name,
                    this.returnType,
                    this.parameters,
                    this.functionBody
            );
        }
    }
}
