package llvm4j.module.config;

import llvm4j.compile.Compilable;
import llvm4j.compile.StringCompiler;

public enum LinkageType implements Compilable {
    PRIVATE("private"),
    INTERNAL("internal"),
    AVAILABLE_EXTERNALLY("available_externally"),
    LINK_ONCE("linkonce"),
    WEAK("weak"),
    COMMON("common"),
    APPENDING("appending"),
    EXTERN_WEAK("extern_weak"),
    LINK_ONCE_ODR("linkonce_odr"),
    LINK_ONCE_WDR("linkonce_wdr"),
    EXTERNAL("external");

    public final String compilesTo;

    LinkageType(String compilesTo) {
        this.compilesTo = compilesTo;
    }

    @Override
    public void compile(StringCompiler stringBuilder) {
        stringBuilder.append(" ").append(this.compilesTo);
    }
}
