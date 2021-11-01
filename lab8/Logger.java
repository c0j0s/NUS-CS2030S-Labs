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
    private final List<Supplier<String>> log;
    private boolean evaluated;

    private Logger(Supplier<T> supplier) {
        this(supplier, new ArrayList<Supplier<String>>());
    }

    private Logger(Supplier<T> supplier, List<Supplier<String>> log) {
        this.log = log;
        this.supplier = supplier;     
        this.cache = Optional.empty();
        evaluated = false;
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
        List<Supplier<String>> newlog = new ArrayList<Supplier<String>>();
        newlog.addAll(log);
        newlog.add(() -> String.format("%s -> %s", get(), mapper.apply(get())));
        return new Logger<U>(() -> mapper.apply(get()), newlog);
    }

    <U> Logger<U> flatMap(Function<? super T, ? extends Logger<? extends U>> mapper) {
        List<Supplier<String>> newlog = new ArrayList<Supplier<String>>();
        newlog.addAll(log);
        newlog.add(() -> mapper.apply(get()).printLog());
        return new Logger<U>(() -> mapper.apply(get()).get(), newlog);    
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

    String printLog() {
        return log.stream().map(x -> x.get()).collect(Collectors.joining("\n"));
    }

    @Override
    public String toString() {
        // to prevent evaluation of last element again
        String logs = printLog();
        String op = "";
        if (log.size() > 0) {
            op = logs.substring(logs.lastIndexOf(">") + 1).trim();
        } 
        // ensure final op has a value
        if (op.isEmpty()) {
            op = get().toString();
        }
        return (String.format("Logger[%s]", op) + "\n" + logs).trim();
    }
}
