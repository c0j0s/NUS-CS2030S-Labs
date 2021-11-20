import java.util.function.Function;
import java.util.stream.Stream;

class Main {
    static double simulate(int seed, int n) {
        double lo = -1.0;
        double hi = 1.0;
        Function<Integer, Double> normal = x -> (hi - lo) * x / (Integer.MAX_VALUE - 1) + lo;

        Long pointsInCircle = 
            Stream.iterate(Rand.of(seed), x -> x.next().next()).limit(n) //10 40 70 100 130 160 190... 
            .map(x -> new Double[] {normal.apply(x.get()), normal.apply(x.next().get())}) //[x,x] [x,x] [x,x]...
            .filter(x -> new Circle(new Point(0.0, 0.0), 1.0).contains(new Point(x[0], x[1]))) //if [x,x] in circle
            .count();

        return 4.0 * pointsInCircle / n;
    }
}
