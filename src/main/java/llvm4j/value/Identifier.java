package llvm4j.value;

import llvm4j.compile.StringCompiler;

public sealed interface Identifier extends Value {
    record Global(String identifier) implements Identifier {

    }

    record Local(String identifier) implements Identifier {

    }

    String identifier();

    @Override
    default void compile(StringCompiler stringBuilder) {
        switch (this) {
            case Global _ -> stringBuilder.append("@");
            case Local _ -> stringBuilder.append("%");
        }
        stringBuilder.append(this.identifier());
    }

}
