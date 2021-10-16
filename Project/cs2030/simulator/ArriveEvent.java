package cs2030.simulator;

class ArriveEvent extends Event {

    /**
     * Constructor for new arrival event.
     * 
     * @param eventTime event timing
     * @param customer a customer
     */
    ArriveEvent(Double eventTime, Customer customer) {
        super(EventState.ARRIVE, eventTime, customer);
    }

    @Override
    public String toString() {
        return super.toString() + " arrives";
    }
}
