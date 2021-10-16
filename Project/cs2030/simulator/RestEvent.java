package cs2030.simulator;

class RestEvent extends HandledEvent {

    RestEvent(Double eventTime, Customer dummy, int serverId) {
        super(EventState.REST, eventTime, dummy, serverId);
    }

    @Override
    public String toString() {
        return super.toString() + " waiting at resting server " + getServerId();
    }
}
