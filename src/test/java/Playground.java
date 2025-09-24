import llvm4j.module.Function;
import llvm4j.module.code.FunctionBody;
import llvm4j.type.Type;
import llvm4j.value.Identifier;

void main() {
    var f = new Function(
            new Identifier.Global("main"),
            new Type.Integer(32),
            List.of(),
            Optional.of(new FunctionBody())
    );
    System.out.println(f);
    System.out.println(f.compile());


    f = new Function(
            new Identifier.Global("smth"),
            new Type.Integer(32),
            List.of(
                    new Function.Parameter(
                            new Identifier.Local("abc"),
                            new Type.Structure(List.of(new Type.Integer(16), new Type.Integer(32)))
                    )
            ),
            Optional.of(new FunctionBody())
    );
    System.out.println(f);
    System.out.println(f.compile());
}
