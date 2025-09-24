package llvm4j.module.value;

import llvm4j.compile.StringCompiler;
import llvm4j.module.type.Type;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public sealed interface Identifier extends Value {
    AtomicInteger INDEX_COUNTER = new AtomicInteger();

    record Global(String identifier) implements Identifier {

    }

    record Local(String identifier) implements Identifier {

    }

    static Global global(String identifier) {
        assert identifier.matches("[-a-zA-Z$._][-a-zA-Z$._0-9]*");
        return new Global(identifier);
    }

    static Local local(String identifier) {
        assert identifier.matches("[-a-zA-Z$._][-a-zA-Z$._0-9]*");
        return new Local(identifier);
    }

    static Local localRandom() {
        return Identifier.local("_" + INDEX_COUNTER.addAndGet(1));
    }

    static Global globalRandom() {
        return Identifier.global("_" + INDEX_COUNTER.addAndGet(1));
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

    default TypeIdentifierPair parameterized(Type other) {
        return new TypeIdentifierPair(other, this);
    }

}
