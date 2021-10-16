package cs2030.simulator;

class HandledEvent extends Event {
    private final int serverId;

    HandledEvent(EventState state, double nextTiming, Customer customer, int serverId) {
        super(state, nextTiming, customer);
        this.serverId = serverId;
    }

    int getServerId() {
        return serverId;
    }
}
