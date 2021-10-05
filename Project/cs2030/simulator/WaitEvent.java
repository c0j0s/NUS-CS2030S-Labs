package cs2030.simulator;

class WaitEvent extends Event {

    private final int serverId;

    public WaitEvent(Event event, int serverId) {
        super(event, EventState.WAIT);
        this.serverId = serverId;
    }

    @Override
    public String toString() {
        return super.toString() + " waits at server " + serverId;
    }
}
