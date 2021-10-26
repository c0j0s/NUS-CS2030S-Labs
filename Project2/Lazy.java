import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

class Lazy<T> {
    private final Supplier<? extends T> supplier;
    private T cache;
    private boolean evaluated;

    private Lazy(T t) {
        this.supplier = () -> t;
        this.cache = t;
        this.evaluated = true;
    }

    private Lazy(Supplier<? extends T> supplier) {
        this.supplier = supplier;
        this.cache = null;
        this.evaluated = false;
    }

    static <T> Lazy<T> ofNullable(T v) {
        return new Lazy<T>(v);
    }

    static <T> Lazy<T> of(Supplier<? extends T> supplier) {
        return new Lazy<T>(supplier);
    }

    Optional<T> get() {
        if (!evaluated) {
            cache = supplier.get();
            evaluated = true;
        }
        return Optional.ofNullable(cache);
    }

    /**
     * evaluated : null -> null | value -> apply(value).
     */
    <R> Lazy<R> map(Function<? super T, ? extends R> mapper) {
        return Lazy.<R>of(() -> get().map(mapper).orElse(null));
    }

    /**
     * evaluated : test(value) ? value : null.
     */
    Lazy<T> filter(Predicate<? super T> predicate) {
        return Lazy.<T>of(() -> get().filter(predicate).orElse(null));
    }

    public T getCache() {
        return cache;
    }

    @Override
    public String toString() {
        return String.format("Lazy[%s]", evaluated ? cache : "?");
    }
}
