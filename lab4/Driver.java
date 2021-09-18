class Driver {
    private final String plate;
    private final int waitTime;
    private final JustRide jr;
    private final RideService sv;

    Driver(String p, int t, RideService sv) {
        this.plate = p;
        this.waitTime = t;
        this.jr = new JustRide();
        this.sv = sv;
    }

    int getWaitTime() {
        return this.waitTime;
    }

    RideService getService(Request r) {
        if (this.jr.computeFare(r) > sv.computeFare(r)) {
            return sv;
        } else {
            return jr;
        }
    }

    @Override
    public String toString() {
        return String.format("%s (%d mins away)", plate, waitTime);
    }
}
