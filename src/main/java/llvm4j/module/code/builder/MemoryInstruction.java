package llvm4j.module.code.builder;

import llvm4j.module.type.Type;
import llvm4j.module.value.Identifier;
import llvm4j.module.value.TypeValuePair;
import llvm4j.module.value.Value;

import java.util.Arrays;

public interface MemoryInstruction<T extends MemoryInstruction<T>> extends BasicBlockBuilderHook {
    default Value alloca(Type type) {
        var id = Identifier.localRandom();
        this.instructions().add(sc -> sc.append("{} = alloca {}", id, type));
        return id;
    }

    default Value alloca(Type type, TypeValuePair size) {
        var id = Identifier.localRandom();
        this.instructions().add(sc -> sc.append("{} = alloca {}, {}", id, type, size));
        return id;
    }

    default Value load(Type type, Value pointer) {
        var id = Identifier.localRandom();
        this.instructions().add(sc -> sc.append("{} = load {}, ptr {}" , id, type, pointer));
        return id;
    }

    enum LoadFlags {
        ATOMIC,
        VOLATILE
    }

    default Value load(Type type, Value pointer, LoadFlags... flags) {
        var id = Identifier.localRandom();
        var flags2 = Arrays.asList(flags);
        this.instructions().add(sc -> sc.append("{} = load ", id)
                .appendIf(() -> flags2.contains(LoadFlags.ATOMIC), sc2 -> sc2.append("atomic"))
                .appendIf(() -> flags2.contains(LoadFlags.VOLATILE), sc2 -> sc2.append("volatile"))
                .append(" {}, ptr {}", type, pointer));
        return id;
    }

    enum StoreFlags {
        ATOMIC,
        VOLATILE
    }

    default T store(TypeValuePair storingValue, Value pointer, StoreFlags... flags) {
        var flags2 = Arrays.asList(flags);
        this.instructions().add(sc -> sc.append("store ")
                .appendIf(() -> flags2.contains(StoreFlags.ATOMIC), sc2 -> sc2.append("atomic"))
                .appendIf(() -> flags2.contains(StoreFlags.VOLATILE), sc2 -> sc2.append("volatile"))
                .append(" {}, ptr {}", storingValue, pointer));
        return (T) this;
    }

    default Value getElementPtr(Type type, Value pointer, TypeValuePair... indices) {
        var id = Identifier.localRandom();
        this.instructions().add(sc -> {
            sc.append("{} = getelementptr {}, ptr {}", id, type, pointer);
            for(var index : indices) {
                sc.append(", {}", index);
            }
        });
        return id;
    }
}
