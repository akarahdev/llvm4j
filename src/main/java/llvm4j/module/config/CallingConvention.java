package llvm4j.module.config;

import llvm4j.compile.Compilable;
import llvm4j.compile.StringCompiler;

public enum CallingConvention implements Compilable {
    C("ccc"),
    FAST("fastcc"),
    COLD("coldcc"),
    GHC("ghccc"),
    CC_11("cc 11"),
    ANY_REG("anyregcc"),
    PRESERVE_MOST("preserve_mostcc"),
    PRESERVE_ALL("preserve_allcc"),
    PRESERVE_NONE("preserve_nonecc"),
    CXX_FAST_TLS("cxx_fast_tlscc"),
    TAIL("tailcc"),
    SWIFT("swiftcc"),
    SWIFT_TAIL("swifttailcc"),
    CF_GUARD_CHECK("cfguard_checkcc");

    public final String compilesTo;

    CallingConvention(String compilesTo) {
        this.compilesTo = compilesTo;
    }

    @Override
    public void compile(StringCompiler stringBuilder) {
        stringBuilder.append(" ").append(this.compilesTo);
    }
}
