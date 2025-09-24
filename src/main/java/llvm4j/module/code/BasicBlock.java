package llvm4j.module.code;

import llvm4j.module.value.Identifier;

import java.util.List;

public record BasicBlock(
        Identifier identifier,
        List<Instruction> instructions
) {
}
