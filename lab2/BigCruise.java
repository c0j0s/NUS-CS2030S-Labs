class BigCruise extends Cruise {
    private static final Double mPerLen = 40.0;
    private static final Double pPerMin = 50.0;

    BigCruise(String id, int arrivalTime, int len, int numPass) {
        super(id, arrivalTime,(int) Math.ceil(len / mPerLen), (int) Math.ceil(numPass / pPerMin));
    }
}
