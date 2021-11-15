import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import java.util.Random;

class Rand<T> {
    private final Supplier<T> seed;
    
    Rand(Supplier<T> s) {
        this.seed = s;
    }

    static Rand of(T s) {
        return new Rand<T>(() -> s);
    }

    T get() {
        return seed.get();
    }

    Rand<T> next() {
        return new Rand<T>(() -> new Random(get()).nextInt(Integer.MAX_VALUE));
    }

    Stream<Integer> stream() {
        return Stream.<Integer>iterate(get(), x -> new Random(x).nextInt(Integer.MAX_VALUE));
    }

    <R> Rand map(Function<T, R> mapper) {
        // issue with swapping map and next
        return new Rand<R>(() -> mapper.apply(get()));
    }

    static <R> Stream<R> randRange(Integer seed, Function<Integer, R> mapper) {
        return Rand.<R>of(seed).stream().map(x -> mapper.apply(x));
    }

    <R> Rand<R> flatMap(Function<T, Rand<R>> mapper) {
        return new Rand<R>(() -> mapper.apply(get()).get());
    }

    public String toString() {
        return String.format("%s", "Rand");
    }

}
