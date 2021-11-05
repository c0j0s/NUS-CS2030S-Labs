import java.lang.IllegalArgumentException;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class Logger<T> {

    private Optional<T> cache;
    private final Supplier<T> supplier;
    private final Supplier<List<Supplier<String>>> history;
    private boolean evaluated;

    private Logger(Supplier<T> supplier) {
        this(supplier, () -> new ArrayList<Supplier<String>>());
    }

    private Logger(Supplier<T> supplier, Supplier<List<Supplier<String>>> history) {
        this.supplier = supplier;     
        this.cache = Optional.empty();
        evaluated = false;
        this.history = history;
    }

    public static <T> Logger<T> of(T v) throws IllegalArgumentException {
        if (Optional.ofNullable(v) == Optional.empty()) {
            throw new IllegalArgumentException("argument cannot be null");
        }
        
        if (v instanceof Logger) {
            throw new IllegalArgumentException("already a Logger");
        }
        
        return new Logger<T>(() -> v);
    }
    
    T get() {
        if (!evaluated) {
            cache = Optional.ofNullable(supplier.get());
            evaluated = true;
        }
        return cache.get();
    }

    <U> Logger<U> map(Function<? super T, ? extends U> mapper) {
        return new Logger<U>(() -> mapper.apply(get()), () -> {
            List<Supplier<String>> newlog = new ArrayList<Supplier<String>>();
            newlog.addAll(history.get());
            newlog.add(() -> get().toString());
            return newlog;
        });
    }

    <U> Logger<U> flatMap(Function<? super T, ? extends Logger<? extends U>> mapper) {
        return new Logger<U>(() -> mapper.apply(get()).get(), () -> {
            List<Supplier<String>> newlog = new ArrayList<Supplier<String>>();
            newlog.addAll(history.get());
            newlog.addAll(mapper.apply(get()).getLog());
            return newlog;
        });    
    }

    Logger<T> test(Predicate<? super T> pred, Logger<T> trueLogger, Logger<T> falseLogger) {
        return pred.test(get()) ? trueLogger : falseLogger;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof Logger) {
            return this.printLog().equals(((Logger<?>) o).printLog());
        }
        return false;
    }

    List<Supplier<String>> getLog() {
        return history.get();
    }

    String printLog() {
        List<String> log = getLog().stream().map(x -> x.get()).collect(Collectors.toList());
        String op = "";
        for (int i = 0; i < log.size() - 1; i++) {
            op += log.get(i) + " -> " + log.get(i + 1) + "\n";      
        }
        if (log.size() > 0) {
            op += log.get(log.size() - 1) + " -> " + get();
        }
        return op;
    }

    @Override
    public String toString() {
        return (String.format("Logger[%s]", get()) + "\n" + printLog()).trim();
    }
}
