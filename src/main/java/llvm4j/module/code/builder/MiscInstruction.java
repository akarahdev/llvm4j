package llvm4j.module.code.builder;

import llvm4j.module.value.Identifier;
import llvm4j.module.value.TypeValuePair;
import llvm4j.module.value.Value;

public interface MiscInstruction<T extends MiscInstruction<T>> extends BasicBlockBuilderHook {
    default T comment(String comment) {
        this.instructions().add(sc -> sc.append("; {}", comment));
        return (T) this;
    }
}
