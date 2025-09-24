package llvm4j.compile;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;

public class StringCompiler {
    StringBuilder inner = new StringBuilder();

    private StringCompiler() {}

    public static StringCompiler create() {
        return new StringCompiler();
    }

    public StringCompiler append(Compilable compilable) {
        compilable.compile(this);
        return this;
    }

    public <T extends Compilable> StringCompiler append(List<T> compilables, String delimiter) {
        int idx = 0;
        for(var c : compilables) {
            this.append(c);
            if(idx != (compilables.size() - 1)) {
                this.append(delimiter);
            }
            idx += 1;
        }
        return this;
    }

    public <T extends Compilable> StringCompiler append(Optional<T> compilable) {
        compilable.ifPresent(this::append);
        return this;
    }

    public StringCompiler append(String s) {
        this.inner.append(s);
        return this;
    }

    public StringCompiler append(char s) {
        this.inner.append(s);
        return this;
    }

    public StringCompiler append(int s) {
        this.inner.append(s);
        return this;
    }

    public StringCompiler append(long s) {
        this.inner.append(s);
        return this;
    }

    public StringCompiler append(double s) {
        this.inner.append(s);
        return this;
    }

    public StringCompiler tabulate() {
        return this.tabulate(4);
    }

    public StringCompiler tabulate(int count) {
        return this.append("\n" + " ".repeat(count));
    }

    public StringCompiler append(String s, Compilable... args) {
        for(var arg : args) {
            int idx = s.indexOf("{}");
            if (idx == -1) break;
            s = s.substring(0, idx) + arg.compile() + s.substring(idx + 2);
        }
        this.append(s);
        return this;
    }

    public StringBuilder builder() {
        return this.inner;
    }

    @Override
    public String toString() {
        return this.inner.toString();
    }
}
