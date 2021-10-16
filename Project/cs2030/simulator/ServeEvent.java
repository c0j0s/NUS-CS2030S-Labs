package cs2030.simulator;

class ServeEvent extends HandledEvent {

    ServeEvent(double nextTiming, Customer customer, int serverId) {
        super(EventState.SERVE, nextTiming, customer, serverId);
    }

    ServeEvent(Event event, int serverId) {
        this(event.getTime(), event.getCustomer(), serverId);
    }

    ServeEvent(Event event, Customer customer, int serverId) {
        this(event.getTime(), customer, serverId);
    }

    @Override
    public String toString() {
        return super.toString() + " serves by server " + getServerId();
    }

}
