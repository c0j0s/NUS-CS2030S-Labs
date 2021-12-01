package cs2030.simulator;

import java.util.Optional;

class ArriveEvent extends Event {

    /**
     * Arrive: new event.
     * @param eventTime arrival time
     * @param customer customer attached to event
     */
    ArriveEvent(Double eventTime, Customer customer) {
        super(EventState.ARRIVE, eventTime, Optional.<Customer>ofNullable(customer));
    }

    @Override
    public String toString() {
        return super.toString() + " arrives";
    }
}
