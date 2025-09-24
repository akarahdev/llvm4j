package llvm4j.module.value;

import llvm4j.compile.Compilable;
import llvm4j.module.TypeValuePair;
import llvm4j.module.type.Type;

public interface Value<V extends Value<V>> extends Compilable {
    @SuppressWarnings("unchecked")
    default <T extends Type<T>> TypeValuePair<T, V> pair(T type) {
        return new TypeValuePair<>(type, (V) this);
    }
}
