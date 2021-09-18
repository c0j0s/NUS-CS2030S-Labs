class Cruise {
    private static final int HUNDRED = 100;
    private static final int MIN_PER_HR = 60;

    private final String identifier;
    private final int arrivalTime;
    private final int numOfLoader;
    private final int serviceTime;

    

    Cruise(String identifier, int arrivalTime, int numOfLoader, int serviceTime) {
        this.identifier = identifier;
        this.arrivalTime = arrivalTime;
        this.numOfLoader = numOfLoader;
        this.serviceTime = serviceTime;
    }
    
    int getNumOfLoadersRequired() {
        return this.numOfLoader;
    }

    int getArrivalTime() {
        //return minutes since midnight
        return (this.getHours(this.arrivalTime) * MIN_PER_HR) + this.getMinutes(this.arrivalTime);
    }

    int getServiceCompletionTime() {            
        return this.getArrivalTime() + this.serviceTime;
    }
    
    private int getHours(int time) {
        // return hours
        return Math.floorDiv(time, HUNDRED);
    }

    private int getMinutes(int time) {
        //return the reminder minutes
        return time % HUNDRED;
    }

    @Override
    public String toString() {
        return String.format("%s@%04d", this.identifier, this.arrivalTime);
    }
}
