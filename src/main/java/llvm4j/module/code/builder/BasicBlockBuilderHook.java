package llvm4j.module.code.builder;

import java.util.List;
import llvm4j.module.code.BasicBlock;
import llvm4j.module.code.FunctionBody;
import llvm4j.module.code.Instruction;
import llvm4j.module.value.Identifier;

public interface BasicBlockBuilderHook {
    public Identifier.Local identifier();
    public List<Instruction> instructions();
    public FunctionBody.Builder functionBuilder();
    public List<BasicBlock> children();
}
