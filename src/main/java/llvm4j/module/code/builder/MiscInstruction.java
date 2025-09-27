package llvm4j.module.code.builder;

public interface MiscInstruction<T extends MiscInstruction<T>>
    extends BasicBlockBuilderHook {
    @SuppressWarnings("unchecked")
    default T comment(String comment) {
        this.instructions().add(sc -> sc.append("; {}", comment));
        return (T) this;
    }
}
