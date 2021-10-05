package cs2030.simulator;

class ArriveEvent extends Event {

    /**
     * Constructor for new arrival event.
     * 
     * @param eventTime event timing
     * @param customer a customer
     */
    public ArriveEvent(Double eventTime, Customer customer) {
        super(EventState.ARRIVE, eventTime, customer);
    }

    /**
     * Constructor for conversion to arrival event.
     * 
     * @param event a privious event
     */
    public ArriveEvent(Event event) {
        super(event, EventState.ARRIVE);
    }

    @Override
    public String toString() {
        return super.toString() + " arrives";
    }
}
