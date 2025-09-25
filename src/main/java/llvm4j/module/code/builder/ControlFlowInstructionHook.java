package llvm4j.module.code.builder;

import llvm4j.module.type.Type;
import llvm4j.module.value.Identifier;
import llvm4j.module.value.TypeValuePair;
import llvm4j.module.code.BasicBlock;
import llvm4j.module.value.Value;

import java.util.List;

public interface ControlFlowInstructionHook<T extends ControlFlowInstructionHook<T>> extends BasicBlockBuilderHook {
    default T ret() {
        this.instructions().add(sc -> sc.append("ret void"));
        return (T) this;
    }

    default T ret(TypeValuePair typeValuePair) {
        this.instructions().add(sc -> sc.append("ret {}", typeValuePair));
        return (T) this;
    }

    default T br(Value condition, BasicBlock.BuildMapper then, BasicBlock.BuildMapper orElse) {
        var bb1 = then.apply(new BasicBlock.Builder(this.functionBuilder())).build();
        var bb2 = orElse.apply(new BasicBlock.Builder(this.functionBuilder())).build();
        this.children().add(bb1);
        this.children().add(bb2);
        this.instructions().add(sc -> sc.append("br i1 {}, label {}, label {}", condition, bb1.identifier(), bb2.identifier()));
        return (T) this;
    }

    default Value callTyped(TypeValuePair functionPointer, List<TypeValuePair> arguments) {
        var id = Identifier.localRandom();
        this.instructions().add(
                sc -> sc.append("{} = call {}", id, functionPointer)
                        .append('(')
                        .append(arguments, ", ")
                        .append(')')
        );
        return id;
    }

    default T callVoid(TypeValuePair functionPointer, List<TypeValuePair> arguments) {
        this.instructions().add(
                sc -> sc.append("call {}", functionPointer)
                        .append('(')
                        .append(arguments, ", ")
                        .append(')')
        );
        return (T) this;
    }
}
