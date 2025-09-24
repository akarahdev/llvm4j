package llvm4j.module.value;

import llvm4j.compile.Compilable;
import llvm4j.module.type.Type;

public interface Value extends Compilable {
    default TypeValuePair typed(Type type) {
        return new TypeValuePair(type, this);
    }
}
