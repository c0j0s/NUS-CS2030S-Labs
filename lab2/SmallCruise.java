class SmallCruise extends Cruise {
    private static final int numOfLoader = 1;
    private static final int serviceTime = 30;

    SmallCruise(String identifier, int arrivalTime) {
        super(identifier, arrivalTime, numOfLoader, serviceTime);
    }
} 
