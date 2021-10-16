package cs2030.simulator;

class WaitEvent extends HandledEvent {

    public WaitEvent(Event event, int serverId) {
        super(EventState.WAIT, event.getTime(), event.getCustomer(), serverId);
    }

    @Override
    public String toString() {
        return super.toString() + " waits at server " + getServerId();
    }
}
