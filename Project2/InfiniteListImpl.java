import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

class InfiniteListImpl<T> implements InfiniteList<T> {
    private final Lazy<T> head;
    private final Supplier<InfiniteList<T>> tail;

    private InfiniteListImpl(Lazy<T> head, Supplier<InfiniteList<T>> tail) {
        this.head = head;
        this.tail = tail;
    }

    static <T> InfiniteList<T> generate(Supplier<? extends T> supplier) {
        Lazy<T> newHead = Lazy.of(supplier);
        Supplier<InfiniteList<T>> newTail = () -> 
                                    InfiniteListImpl.<T>generate(supplier);
        return new InfiniteListImpl<T>(newHead, newTail);
    }

    static <T> InfiniteList<T> iterate(T seed, Function<T, T> next) {
        Lazy<T> newHead = Lazy.of(() -> seed);
        Supplier<InfiniteList<T>> newTail = () -> 
                        InfiniteListImpl.<T>iterate(next.apply(seed), next);
        return new InfiniteListImpl<T>(newHead, newTail);
    }

    @Override
    public InfiniteList<T> peek() {
        head.get().ifPresent(x -> System.out.println(x));
        return tail.get();
    }

    @Override
    public <R> InfiniteList<R> map(Function<? super T, ? extends R> mapper) {
        Lazy<R> newHead = head.map(mapper);
        Supplier<InfiniteList<R>> newTail = () -> tail.get().map(mapper);
        return new InfiniteListImpl<R>(newHead, newTail);
    }

    @Override
    public InfiniteList<T> filter(Predicate<? super T> predicate) {
        Lazy<T> newHead = head.filter(predicate);
        Supplier<InfiniteList<T>> newTail = () -> tail.get().filter(predicate);
        return new InfiniteListImpl<T>(newHead, newTail);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    /**
     * Infinite loop issue: 
     * unable to differentiate filtered null element or terminating null condition. 
     */
    @Override
    public InfiniteList<T> limit(long n) {
        if (n > 0) {
            return new InfiniteListImpl<T>(head, () -> head.get() != Optional.empty() 
                    ? n != 1 ? tail.get().limit(n - 1) : new EmptyList<T>()
                    : tail.get().limit(n));
        } else {
            return new EmptyList<T>();
        }
    }

    /**
     * Return emptylist if old head has value but new head become null due to predicate.
     */
    @Override
    public InfiniteList<T> takeWhile(Predicate<? super T> predicate) {
        Lazy<T> newHead = head.filter(predicate);
        Supplier<InfiniteList<T>> newTail = () -> 
                head.get() != Optional.empty() && newHead.get() == Optional.empty()
                ? new EmptyList<T>()
                : tail.get().takeWhile(predicate);
        return new InfiniteListImpl<>(newHead, newTail);
    }

    @Override
    public Object[] toArray() {
        Object[] oldArr = tail.get().toArray();
        if (head.get() != Optional.empty()) {
            Object[] newArr = new Object[oldArr.length + 1];
            newArr[0] = head.getCache();

            for (int i = 0; i < oldArr.length; i++) {
                newArr[i + 1] = oldArr[i];
            }

            oldArr = newArr;
        }
        return oldArr;
    }

    @Override
    public long count() {
        if (head.get() != Optional.empty()) {
            return tail.get().count() + 1;
        }
        return tail.get().count();
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator) {
        return tail.get().reduce(
            head.get().map(x -> accumulator.apply(identity, x)).orElse(identity), 
            accumulator);
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        head.get().ifPresent(action);
        tail.get().forEach(action);
    }
}
