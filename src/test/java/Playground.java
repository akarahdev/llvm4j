import llvm4j.module.Function;
import llvm4j.module.type.Type;
import llvm4j.module.value.Identifier;

void main() {
    var f = Function.builder(new Identifier.Global("main"))
            .withReturnType(new Type.Integer(32))
            .build();
    System.out.println(f);
    System.out.println(f.compile());


    f = Function.builder(new Identifier.Global("abc"))
            .withReturnType(new Type.Void())
            .withParameter(new Type.Integer(32).pair(new Identifier.Local("param1")))
            .build();
    System.out.println(f);
    System.out.println(f.compile());
}
