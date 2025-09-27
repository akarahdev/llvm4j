package llvm4j.module.code.builder;

import java.util.Arrays;
import llvm4j.module.type.Type;
import llvm4j.module.value.Identifier;
import llvm4j.module.value.TypeValuePair;
import llvm4j.module.value.Value;

public interface ConversionInstruction<T extends ConversionInstruction<T>>
    extends BasicBlockBuilderHook {
    default Value trunc(Type oldType, Value value, Type newType) {
        var id = Identifier.localRandom();
        this.instructions().add(sc ->
            sc.append("{} = trunc {} {} to {}", id, oldType, value, newType)
        );
        return id;
    }

    default Value zext(Type oldType, Value value, Type newType) {
        var id = Identifier.localRandom();
        this.instructions().add(sc ->
            sc.append("{} = zext {} {} to {}", id, oldType, value, newType)
        );
        return id;
    }

    default Value sext(Type oldType, Value value, Type newType) {
        var id = Identifier.localRandom();
        this.instructions().add(sc ->
            sc.append("{} = sext {} {} to {}", id, oldType, value, newType)
        );
        return id;
    }
}
