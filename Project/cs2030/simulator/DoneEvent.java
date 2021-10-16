package cs2030.simulator;

class DoneEvent extends HandledEvent {

    DoneEvent(Event event, double nextTiming, int serverId) {
        super(EventState.DONE, nextTiming, event.getCustomer(), serverId);
    }
    
    @Override
    public String toString() {
        return super.toString() + " done serving by server " + getServerId();
    }
}
