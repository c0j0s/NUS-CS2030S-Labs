package cs2030.simulator;

abstract class Event implements Comparable<Event> {
    private final EventState state;
    private final double eventTime;
    private final Customer customer;

    Event(EventState state, double eventTime, Customer customer) {
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

    Customer getCustomer() {
        return this.customer;
    }

    @Override
    public String toString() {
        return String.format("%.3f %s", eventTime, customer);
    }

    @Override
    public int compareTo(Event event) {
        int diff = Double.compare(this.eventTime, event.eventTime);
        if (diff == 0) {
            return this.customer.compareTo(event.customer);
        }
        return diff;
    }

}
