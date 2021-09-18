package cs2030.simulator;

class ServeEvent extends Event {

    private final int serverId;

    ServeEvent(double nextTiming, Customer customer, int serverId) {
        super(EventState.SERVE, nextTiming, customer);
        this.serverId = serverId;
    }

    ServeEvent(Event event, double nextTiming, int serverId) {
        this(nextTiming, event.getCustomer(), serverId);
    }

    @Override
    public String toString() {
        return super.toString() + " serves by server " + serverId;
    }

}
