package cs2030.simulator;

import java.util.Optional;

abstract class Event implements Comparable<Event> {
    private final EventState state;
    private final double eventTime;
    private final Optional<Customer> customer;

    Event(EventState state, double eventTime, Optional<Customer> customer) {
        this.state = state;
        this.eventTime = eventTime;
        this.customer = customer;
    }

    EventState getState() {
        return this.state;
    }

    double getTime() {
        return this.eventTime;
    }

    Optional<Customer> getCustomer() {
        return this.customer;
    }

    @Override
    public String toString() {
        return String.format("%.3f %s", eventTime, customer.map(c -> c.toString()).orElse(""));
    }

    @Override
    public int compareTo(Event event) {
        int diff = Double.compare(this.eventTime, event.eventTime);
        if (diff == 0) {
            return getCustomer().flatMap(c1 -> 
                event.getCustomer().map(c2 -> c1.compareTo(c2))
            ).orElse(diff);
        }
        return diff;
    }

}
