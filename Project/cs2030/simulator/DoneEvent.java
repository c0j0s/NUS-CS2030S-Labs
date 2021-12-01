package cs2030.simulator;

class DoneEvent extends Event {

    /**
     * Serve to Done: new state.
     * @param event serve event
     * @param nextTiming new event timing
     */
    DoneEvent(Event event, double nextTiming) {
        super(EventState.DONE, nextTiming, event.getCustomer());
    }

    @Override
    public String toString() {
        return super.toString() + " done serving by ";
    }
}
