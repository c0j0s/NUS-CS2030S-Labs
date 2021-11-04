package cs2030.simulator;

class LeaveEvent extends Event {

    /**
     * Arrive to Leave: new state.
     * @param event arrive event
     */
    LeaveEvent(Event event) {
        super(EventState.LEAVE, event.getTime(), event.getCustomer());
    }

    @Override
    public String toString() {
        return super.toString() + " leaves";
    }
}
