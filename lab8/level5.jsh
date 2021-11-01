Logger<Integer> add(Logger<Integer> l, int o) {
    return l.map(x -> x + o);    
}

Logger<Integer> sum(int n) {
    if (n == 0) {
        return Logger.<Integer>of(0);
    } else {
        return add(sum(n - 1), n);    
    }    
}

Logger<Integer> f(int n) {
    Logger<Integer> logger = Logger.of(n);
    return logger.test(x -> x == 1, 
                logger, 
                logger.test(x -> x % 2 == 0, 
                    logger.map(y -> y / 2).flatMap(x -> f(n / 2)),
                    logger.map(y -> 3 * y).map(z -> z + 1).flatMap(x -> f(3 * n + 1))));
}
