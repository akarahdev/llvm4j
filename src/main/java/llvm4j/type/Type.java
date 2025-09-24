package llvm4j.type;

import llvm4j.compile.Compilable;
import llvm4j.compile.StringCompiler;
import llvm4j.value.Identifier;

import java.util.List;

public sealed interface Type extends Compilable {
    record Named(Identifier identifier) implements Type {
        @Override
        public void compile(StringCompiler stringBuilder) {
            stringBuilder.append(identifier);
        }
    }

    record Integer(int bits) implements Type {
        @Override
        public void compile(StringCompiler stringBuilder) {
            stringBuilder.append('i').append(this.bits);
        }
    }

    record Structure(List<Type> types) implements Type {
        @Override
        public void compile(StringCompiler stringBuilder) {
            stringBuilder.append('{')
                    .append(this.types, ", ")
                    .append('}');
        }
    }

    record Pointer() implements Type {
        @Override
        public void compile(StringCompiler stringBuilder) {
            stringBuilder.append("ptr");
        }
    }
}
