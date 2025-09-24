package llvm4j.module.code.builder;

import llvm4j.module.TypeValuePair;
import llvm4j.module.code.BasicBlock;
import llvm4j.module.type.Type;
import llvm4j.module.value.Identifier;
import llvm4j.module.value.Value;

public interface ArithmeticInstructionHook<T extends ArithmeticInstructionHook<T>> extends BasicBlockBuilderHook {
    default Value<?> arithmetic(String op, Type<?> type, Value<?> lhs, Value<?> rhs) {
        var id = Identifier.localRandom();
        this.instructions().add(sc -> sc.append("{} = " + op + " {} {}, {}", id, type, lhs, rhs));
        return id;
    }

    default Value<?> add(Type<?> type, Value<?> lhs, Value<?> rhs) {
        return arithmetic("add", type, lhs, rhs);
    }

    default Value<?> fadd(Type<?> type, Value<?> lhs, Value<?> rhs) {
        return arithmetic("fadd", type, lhs, rhs);
    }

    default Value<?> sub(Type<?> type, Value<?> lhs, Value<?> rhs) {
        return arithmetic("sub", type, lhs, rhs);
    }

    default Value<?> fsub(Type<?> type, Value<?> lhs, Value<?> rhs) {
        return arithmetic("fsub", type, lhs, rhs);
    }

    default Value<?> mul(Type<?> type, Value<?> lhs, Value<?> rhs) {
        return arithmetic("mul", type, lhs, rhs);
    }

    default Value<?> fmul(Type<?> type, Value<?> lhs, Value<?> rhs) {
        return arithmetic("fmul", type, lhs, rhs);
    }

    default Value<?> sdiv(Type<?> type, Value<?> lhs, Value<?> rhs) {
        return arithmetic("sdiv", type, lhs, rhs);
    }

    default Value<?> udiv(Type<?> type, Value<?> lhs, Value<?> rhs) {
        return arithmetic("udiv", type, lhs, rhs);
    }

    default Value<?> fdiv(Type<?> type, Value<?> lhs, Value<?> rhs) {
        return arithmetic("fdiv", type, lhs, rhs);
    }

    default Value<?> srem(Type<?> type, Value<?> lhs, Value<?> rhs) {
        return arithmetic("srem", type, lhs, rhs);
    }

    default Value<?> urem(Type<?> type, Value<?> lhs, Value<?> rhs) {
        return arithmetic("urem", type, lhs, rhs);
    }

    default Value<?> frem(Type<?> type, Value<?> lhs, Value<?> rhs) {
        return arithmetic("frem", type, lhs, rhs);
    }
}
