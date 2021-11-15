class Main {

    //public void main(String[] args) {
    
    //}

    static double simulate(int seed, int n) {
        double lo = -1.0;
        double hi = 1.0;
        return Rand.randRange(seed, x -> (hi - lo) * x / (Integer.MAX_VALUE - 1) + lo).limit(n).flatMap(x -> Rand.of(x).map(y -> List.of(x,y)).stream().map(x -> x * 4).reduce(x -> x);

    }
}
