package cs2030.simulator;

class StatsManager {
    private final int numServe;
    private final int numLeave;
    private final double waitTime;

    StatsManager() {
        this.numServe = 0;
        this.numLeave = 0;
        this.waitTime = 0;
    }

    StatsManager(int numServe, int numLeave, double waitTime) {
        this.numServe = numServe;
        this.numLeave = numLeave;
        this.waitTime = waitTime;
    }

    StatsManager recordEvent(Event event) {
        switch (event.getState()) {
            case SERVE:
                double time = waitTime + event.getCustomer().getWaitTime();
                return new StatsManager(numServe, numLeave, time);
            case DONE:
                return new StatsManager(numServe + 1, numLeave, waitTime);
            case LEAVE:
                return new StatsManager(numServe, numLeave + 1, waitTime);
            default:
                return this;
        }
    }

    String report() {
        return this.toString();
    }

    @Override
    public String toString() {
        return String.format("[%.3f %d %d]", waitTime / numServe, numServe, numLeave);
    }
}
