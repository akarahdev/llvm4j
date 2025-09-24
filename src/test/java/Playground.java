import llvm4j.module.Function;
import llvm4j.module.code.FunctionBody;
import llvm4j.module.type.Type;
import llvm4j.module.value.Constant;
import llvm4j.module.value.Identifier;

void main() {
    var f = Function.builder(new Identifier.Global("main"))
            .withReturnType(Type.integer(32))
            .build();
    System.out.println(f);
    System.out.println(f.compile());


    f = Function.builder(new Identifier.Global("main"))
            .withReturnType(Type.integer(32))
            .withBody(
                    FunctionBody.builder()
                            .withCode(
                                    bb -> bb.ret(
                                            bb.extractValue(
                                                    Constant.array(
                                                            Type.integer(32),
                                                            Constant.integer(10),
                                                            Constant.integer(20),
                                                            Constant.integer(30)
                                                    ).pair(Type.array(3, Type.integer(32))),
                                                    1
                                            ).pair(Type.integer(32))
                                    )
                            )
                            .build()
            )
            .build();
    System.out.println(f);
    System.out.println(f.compile());
}
