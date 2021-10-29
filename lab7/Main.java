import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.OptionalDouble;

class Main {

    static long countTwinPrimes(int n) {
        return Main.primes(n).filter(x -> Main.isPrime(x - 2) || Main.isPrime(x + 2))
                    .distinct().count();
    }

    static IntStream primes(int n) {
        return IntStream.rangeClosed(2, n).filter(x -> isPrime(x));
    }

    static boolean isPrime(int n) {
        return IntStream.range(1, n).filter(x -> n % x == 0).count() == 1;
    }

    static String reverse(String str) {
        return IntStream.iterate(str.length() - 1, i -> i - 1)
                .limit(str.length())
                .mapToObj(x -> str.charAt(x))
                .map(c -> c.toString()).collect(Collectors.joining());
    }

    static long countRepeats(int... array) {
        int[] diff = IntStream.range(0, array.length - 1)
                        .filter(x -> array[x] == array[x + 1]).toArray();
        return diff.length - IntStream.range(0, diff.length - 1)  
                        .filter(x -> diff[x + 1] - diff[x] == 1).count();
    }

    static double normalizedMean(Stream<Integer> stream) {
        int[] arr = stream.flatMapToInt(x -> IntStream.of(x)).toArray();
        OptionalInt max = OptionalInt.of(IntStream.of(arr).max().orElse(0));
        OptionalInt min = OptionalInt.of(IntStream.of(arr).min().orElse(0));
        OptionalDouble avg = OptionalDouble.of(IntStream.of(arr).average().orElse(0));
        
        return Optional.of((avg.getAsDouble() - min.getAsInt()) / (max.getAsInt() - min.getAsInt()))
                    .filter(x -> !Double.isNaN(x)).orElse(0.0);
    }
}
