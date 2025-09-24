import llvm4j.module.Module;
import llvm4j.module.type.Type;
import llvm4j.module.value.Constant;
import llvm4j.module.value.Identifier;
import llvm4j.module.value.Value;

void main() {
    var structureType = Type.struct(List.of(Type.integer(32), Type.integer(32)));
    var module = Module.builder()
            .withFunction(
                    Identifier.global("main"),
                    fb -> fb.withReturnType(Type.integer(32))
                            .withCode(bb -> {
                                Value struct = Constant.undef();
                                struct = bb.insertValue(
                                        struct.typed(structureType),
                                        Constant.integer(20).typed(Type.integer(32)),
                                        0
                                );
                                struct = bb.insertValue(
                                        struct.typed(structureType),
                                        Constant.integer(40).typed(Type.integer(32)),
                                        1
                                );
                                return bb.ret(
                                        bb.extractValue(struct.typed(structureType), 1)
                                                .typed(Type.integer(32))
                                );
                            })
            )
            .withFunction(
                    Identifier.global("doubler"),
                    fb -> fb.withReturnType(Type.integer(32))
                            .withParameter(Identifier.local("var").parameterized(Type.integer(32)))
                            .withCode(bb -> bb.ret(
                                    bb.mul(Type.integer(32), Identifier.local("var"), Constant.integer(2))
                                            .typed(Type.integer(32))
                            ))
            )
            .build();
    try {
        module.emit(Path.of("./build/module.ll"));
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}
