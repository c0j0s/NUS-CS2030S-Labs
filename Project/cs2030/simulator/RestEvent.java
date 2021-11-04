package cs2030.simulator;

import java.util.Optional;

class RestEvent extends Event {

    /**
     * Done -> Rest: new State.
     * @param eventTime new event time
     * @param customer new customer
     */
    RestEvent(Double eventTime, Optional<Customer> customer) {
        super(EventState.REST, eventTime, customer);
    }


    @Override
    public String toString() {
        return super.toString() + " resting server: ";
    }
}
