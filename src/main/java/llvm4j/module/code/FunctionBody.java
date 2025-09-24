package llvm4j.module.code;

import llvm4j.compile.Compilable;
import llvm4j.compile.StringCompiler;

public record FunctionBody(BasicBlock entry) implements Compilable {
    @Override
    public void compile(StringCompiler stringBuilder) {
        stringBuilder.append("{")
                .append(entry)
                .append("\n}");
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        BasicBlock entry;

        public Builder withCode(BasicBlock.BuildMapper consumer) {
            var bbb = new BasicBlock.Builder(this);
            this.entry = consumer.apply(bbb).build();
            return this;
        }

        public FunctionBody build() {
            return new FunctionBody(this.entry);
        }
    }
}
