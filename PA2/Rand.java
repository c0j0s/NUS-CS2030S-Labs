import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import java.util.Random;

class Rand<T> {
    private final int seed;
    private final Function<Integer, T> f;
    
    Rand(Supplier<Integer> s, Function<Integer, T> f) {
        this.seed = s.get();
        this.f = f;
    }

    static Rand<Integer> of(int s) {
        return new Rand<Integer>(() -> s, x -> x);
    }

    T get() {
        return f.apply(seed);
    }

    Rand<T> next() {
        return new Rand<T>(() -> new Random(seed).nextInt(Integer.MAX_VALUE), f);
    }

    Stream<T> stream() {
        return Stream.iterate(seed, x -> new Random(x).nextInt(Integer.MAX_VALUE)).map(f);
    }

    <R> Rand<R> map(Function<T, R> mapper) {
        return new Rand<R>(() -> seed, mapper.compose(f));
    }

    static <R> Stream<R> randRange(Integer seed, Function<Integer, R> mapper) {
        return Rand.<R>of(seed).stream().map(x -> mapper.apply(x));
    }

    <R> Rand<R> flatMap(Function<T, Rand<R>> mapper) {
        Function<Rand<R>, R> uw = x -> x.get();
        return new Rand<R>(() -> seed, uw.compose(mapper).compose(f));
    }

    public String toString() {
        return String.format("%s", "Rand");
    }

}
