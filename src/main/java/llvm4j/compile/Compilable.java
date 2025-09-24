package llvm4j.compile;

public interface Compilable {
    void compile(StringCompiler stringBuilder);

    default String compile() {
        var sc = StringCompiler.create();
        sc.append(this);
        return sc.toString();
    }
}
