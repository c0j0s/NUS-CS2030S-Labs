package cs2030.simulator;

import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A server class.
 */
abstract class Server implements Comparable<Server> {

    private final int id;
    private final ServerState state;
    private final double nextTiming;
    private final int queueLimit;

    private final Optional<Customer> servingCustomer;
    private final LinkedList<Customer> queue;
    private final int queueOwnerId;

    public Server(int id, ServerState state, double nextTiming, int queueLimit, 
        Optional<Customer> servingCustomer, LinkedList<Customer> queue, int queueOwnerId) {
            
        this.id = id;
        this.state = state;
        this.nextTiming = nextTiming;
        this.queueLimit = queueLimit;
        this.servingCustomer = servingCustomer;
        this.queue = queue;
        this.queueOwnerId = queueOwnerId;
    }

    int getId() {
        return id;
    }

    ServerState getState() {
        return state;
    }

    double getNextTiming() {
        return nextTiming;
    }

    Optional<Customer> getServingCustomer() {
        return servingCustomer;
    }

    LinkedList<Customer> getQueue() {
        return queue;
    }

    int getQueueLimit() {
        return queueLimit;
    }

    int getQueueOwnerId() {
        return queueOwnerId;
    }

    String getQueueOwner() {
        return toString();
    }

    /**
     * Takes in an event and return a new server based on current state.
     * @param event    arrive event
     * @param consumer handler to handle output event of type: serve/wait/leave.
     * @return Server
     */
    abstract Server scheduleServe(Event event, Consumer<Event> consumer);

    /**
     * Takes in an event and return a new server based on current state.
     * @param event    serve event
     * @param consumer handler to handle output event of type: done.
     * @return Server
     */
    abstract Server scheduleDone(Event event, Consumer<Event> consumer);

    
    /**
     * Takes in an event and return rest or serve event.
     * @param event                 done event
     * @param getRestTime           rest time supplier
     * @param hasCustomerConsumer   handler to handle output event of type: rest/serve.
     * @param noCustomer            handler to handle idle state
     * @return Server
     */
    abstract Server scheduleRestOrServe(Event event, Supplier<Double> getRestTime, 
        Consumer<Event> hasCustomerConsumer, Runnable noCustomer);

    /**
     * Takes in an event and return serve event.
     * @param event                 wake event
     * @param hasCustomerConsumer   handler to handle output event of type: serve.
     * @param noCustomer            handler to handle idle state
     * @return Server
     */
    abstract Server scheduleServeNext(Event event, Consumer<Event> hasCustomerConsumer, 
        Runnable noCustomer);

    /**
     * Self Checkout trigger.
     * @return T/F
     */
    abstract boolean isSelfCheckOut();

    /**
     * Determine if queue open for queuing.
     * @return T/F
     */
    boolean queueCanWait() {
        return getQueue().size() < getQueueLimit();
    }

    /**
     * Determine if server is handeling customer.
     * @param customer matching customer
     * @return T/F
     */
    boolean hasCustomer(Customer customer) {
        return getServingCustomer()
                .map(c -> c.equals(customer))
                .orElse(getQueue().stream().anyMatch(c -> c.equals(customer)));
    }

    /**
     * Determine a new state for server base on given or current queue state.
     * @param isReleasingCustomer trigger to ignore customer in serving slot
     * @return ServerState
     */
    ServerState determineNextState(boolean isReleasingCustomer) {
        if (getServingCustomer().map(c -> false).orElse(true) && getQueue().size() == 0) {
            return ServerState.IDLE;
        }

        if (isReleasingCustomer && getQueue().size() == 0) {
            return ServerState.IDLE;
        }

        if (queueCanWait()) {
            return ServerState.SERVING;
        } else {
            return ServerState.FULL;
        }
    }

    @Override
    public int compareTo(Server o) {

        // rank by state: lower state ordinal -> higher priority
        int d = this.state.getRank() - o.state.getRank();
        if (d != 0) {
            return d;
        }

        if (queueCanWait() && o.queueCanWait()) {
            // rank by server id
            d = this.id - o.id;
            if (d != 0) {
                return d;
            }
        }

        // rank by queue size
        return this.getQueue().size() - o.getQueue().size();
    }

    @Override
    public String toString() {
        return "server " + id;
    }

}
