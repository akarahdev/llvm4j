package llvm4j.module.code.builder;

import llvm4j.module.value.TypeValuePair;
import llvm4j.module.value.Identifier;
import llvm4j.module.value.Value;

public interface AggregateInstructions<T extends AggregateInstructions<T>> extends BasicBlockBuilderHook {
    default Value extractValue(TypeValuePair aggregate, int index) {
        var id = Identifier.localRandom();
        this.instructions().add(sc -> sc.append("{} = extractvalue {}, {}", id, aggregate, index));
        return id;
    }

    default Value insertValue(TypeValuePair aggregate, TypeValuePair element, int index) {
        var id = Identifier.localRandom();
        this.instructions().add(sc -> sc.append("{} = insertvalue {}, {}, {}", id, aggregate, element, index));
        return id;
    }
}
