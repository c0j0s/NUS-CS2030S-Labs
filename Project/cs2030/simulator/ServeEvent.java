package cs2030.simulator;

import java.util.Optional;

class ServeEvent extends Event {

    /**
     * Arrive to Serve: new state.
     * @param event arrive event
     * @param nextTiming new event timing
     */
    ServeEvent(Event event) {
        super(EventState.SERVE, event.getTime(), event.getCustomer());
    }

    /**
     * Done to Serve: new state and customer.
     * @param event done event
     * @param customer new customer
     */
    ServeEvent(Event event, Optional<Customer> customer) {
        super(EventState.SERVE, event.getTime(), customer);
    }

    @Override
    public String toString() {
        return super.toString() + " serves by ";
    }
}
