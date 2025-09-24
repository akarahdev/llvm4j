import llvm4j.module.Module;
import llvm4j.module.type.Type;
import llvm4j.module.value.Constant;
import llvm4j.module.value.Identifier;

void main() {
    var module = Module.builder()
            .withFunction(
                    Identifier.global("main"),
                    fb -> fb.withReturnType(Type.integer(32))
                            .withParameter(Identifier.localRandom().parameterized(Type.ptr()))
                            .withVarargs()
                            .withCode(bb -> {
                                var myVar = bb.alloca(Type.integer(32));

                                // this is effectively a no-op
                                myVar = bb.getElementPtr(Type.integer(32), myVar, Constant.integer(0).typed(Type.integer(32)));

                                bb.store(Constant.integer(20).typed(Type.integer(32)), myVar);
                                bb.store(
                                        bb.add(
                                                Type.integer(32),
                                                bb.load(Type.integer(32), myVar),
                                                Constant.integer(5)
                                        ).typed(Type.integer(32)),
                                        myVar
                                );
                                return bb.ret(
                                        bb.load(Type.integer(32), myVar)
                                                .typed(Type.integer(32))
                                );
                            })
            )
            .build();
    try {
        module.emit(Path.of("./build/module.ll"));
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}
