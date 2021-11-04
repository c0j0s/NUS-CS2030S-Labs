package cs2030.simulator;

class WaitEvent extends Event {

    /**
     * Arrive to Wait: new state.
     * @param event arrive event
     */
    WaitEvent(Event event) {
        super(EventState.WAIT, event.getTime(), event.getCustomer());
    }

    @Override
    public String toString() {
        return super.toString() + " waits at ";
    }
}
