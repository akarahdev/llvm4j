package llvm4j.module.value;

import llvm4j.compile.StringCompiler;

public sealed interface Identifier<V extends Value<V>> extends Value<V> {
    record Global(String identifier) implements Identifier<Global> {

    }

    record Local(String identifier) implements Identifier<Local> {

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
