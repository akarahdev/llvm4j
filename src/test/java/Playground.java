import llvm4j.module.Module;
import llvm4j.module.type.Type;
import llvm4j.module.value.Constant;
import llvm4j.module.value.Identifier;

void main() {
    var module = Module.builder()
            .withGlobalVariable(
                    Identifier.global(".str"),
                    Constant.c_str("Hello world!\\0A\\00")
            )
            .withFunction(
                    Identifier.global("printf"),
                    fb -> fb.withReturnType(Type.integer(32))
                            .withParameter(Identifier.localRandom().parameterized(Type.ptr()))
                            .withVarargs()
            )
            .withFunction(
                    Identifier.global("main"),
                    fb -> fb.withReturnType(Type.integer(32))
                            .withCode(bb -> {
                                bb.call(
                                        Identifier.global("printf").typed(
                                                Type.function(Type.integer(32), List.of(Type.ptr()), true)
                                        ),
                                        List.of(
                                                Identifier.global(".str")
                                                        .typed(Type.ptr())
                                        )
                                );
                                return bb.ret(Constant.integer(0).typed(Type.integer(32)));
                            })
            )
            .build();
    try {
        module.emit(Path.of("./build/module.ll"));
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}
