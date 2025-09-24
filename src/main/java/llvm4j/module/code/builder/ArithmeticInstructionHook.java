package llvm4j.module.code.builder;

import llvm4j.module.type.Type;
import llvm4j.module.value.Identifier;
import llvm4j.module.value.Value;

public interface ArithmeticInstructionHook<T extends ArithmeticInstructionHook<T>> extends BasicBlockBuilderHook {
    default Value binaryOperation(String op, Type type, Value lhs, Value rhs) {
        var id = Identifier.localRandom();
        this.instructions().add(sc -> sc.append("{} = " + op + " {} {}, {}", id, type, lhs, rhs));
        return id;
    }

    default Value add(Type type, Value lhs, Value rhs) {
        return binaryOperation("add", type, lhs, rhs);
    }

    default Value fadd(Type type, Value lhs, Value rhs) {
        return binaryOperation("fadd", type, lhs, rhs);
    }

    default Value sub(Type type, Value lhs, Value rhs) {
        return binaryOperation("sub", type, lhs, rhs);
    }

    default Value fsub(Type type, Value lhs, Value rhs) {
        return binaryOperation("fsub", type, lhs, rhs);
    }

    default Value mul(Type type, Value lhs, Value rhs) {
        return binaryOperation("mul", type, lhs, rhs);
    }

    default Value fmul(Type type, Value lhs, Value rhs) {
        return binaryOperation("fmul", type, lhs, rhs);
    }

    default Value sdiv(Type type, Value lhs, Value rhs) {
        return binaryOperation("sdiv", type, lhs, rhs);
    }

    default Value udiv(Type type, Value lhs, Value rhs) {
        return binaryOperation("udiv", type, lhs, rhs);
    }

    default Value fdiv(Type type, Value lhs, Value rhs) {
        return binaryOperation("fdiv", type, lhs, rhs);
    }

    default Value srem(Type type, Value lhs, Value rhs) {
        return binaryOperation("srem", type, lhs, rhs);
    }

    default Value urem(Type type, Value lhs, Value rhs) {
        return binaryOperation("urem", type, lhs, rhs);
    }

    default Value frem(Type type, Value lhs, Value rhs) {
        return binaryOperation("frem", type, lhs, rhs);
    }

    default Value shr(Type type, Value lhs, Value rhs) {
        return binaryOperation("shr", type, lhs, rhs);
    }

    default Value lshr(Type type, Value lhs, Value rhs) {
        return binaryOperation("lshr", type, lhs, rhs);
    }

    default Value and(Type type, Value lhs, Value rhs) {
        return binaryOperation("and", type, lhs, rhs);
    }

    default Value or(Type type, Value lhs, Value rhs) {
        return binaryOperation("or", type, lhs, rhs);
    }
}
