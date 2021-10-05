package cs2030.simulator;

class DoneEvent extends Event {

    private final int serverId;

    DoneEvent(Event event, double nextTiming, int serverId) {
        super(event, EventState.DONE, nextTiming);
        this.serverId = serverId;
    }
    
    @Override
    public String toString() {
        return super.toString() + " done serving by server " + serverId;
    }
}
