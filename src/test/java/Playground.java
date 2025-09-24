import llvm4j.module.Function;
import llvm4j.module.code.FunctionBody;
import llvm4j.module.type.Type;
import llvm4j.module.value.Constant;
import llvm4j.module.value.Identifier;

void main() {
    var f = Function.builder(new Identifier.Global("main"))
            .withReturnType(new Type.Integer(32))
            .build();
    System.out.println(f);
    System.out.println(f.compile());


    f = Function.builder(new Identifier.Global("main"))
            .withReturnType(new Type.Integer(32))
            .withBody(
                    FunctionBody.builder()
                            .withCode(
                                    bb -> bb.br(
                                            bb.add(
                                                    new Type.Integer(1),
                                                    Constant.bool(true),
                                                    Constant.bool(false)
                                            ),
                                            bb2 -> bb2.ret(Constant.integer(100).pair(new Type.Integer(32))),
                                            bb2 -> bb2.ret(Constant.integer(200).pair(new Type.Integer(32)))
                                    )
                            )
                            .build()
            )
            .build();
    System.out.println(f);
    System.out.println(f.compile());
}
