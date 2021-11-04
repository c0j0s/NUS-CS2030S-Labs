package cs2030.simulator;

import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A self checkout server that requires a master queue for initialisation.
 */
class SelfCheckoutServer extends HumanServer {

    SelfCheckoutServer(int id, ServerState state, double nextTiming, 
        Optional<Customer> servingCustomer, int queueLimit, 
        LinkedList<Customer> selfCheckoutQueue, int queueOwnerId) {
        super(id, state, nextTiming, servingCustomer, queueLimit, selfCheckoutQueue, queueOwnerId);
    }

    // initial creation
    SelfCheckoutServer(int id, int queueLimit, LinkedList<Customer> queue, int queueOwnerId) {
        this(id, ServerState.IDLE, 0d, Optional.empty(), queueLimit, queue, queueOwnerId);
    }

    // wrapper constructor
    SelfCheckoutServer(Server server) {
        this(server.getId(), server.getState(), server.getNextTiming(), 
            server.getServingCustomer(), server.getQueueLimit(), 
            server.getQueue(), server.getQueueOwnerId());
    }

    @Override
    Server scheduleServe(Event event, Consumer<Event> consumer) {
        return new SelfCheckoutServer(super.scheduleServe(event, consumer));
    }

    @Override
    Server scheduleDone(Event event, Consumer<Event> consumer) {
        return new SelfCheckoutServer(super.scheduleDone(event, consumer));
    }

    @Override
    Server scheduleRestOrServe(Event event, Supplier<Double> getRestTime, 
        Consumer<Event> hasCustomerConsumer, Runnable noCustomer) {
        return new SelfCheckoutServer(
            super.scheduleRestOrServe(event, () -> 0d, hasCustomerConsumer, noCustomer)
            );
    }

    @Override
    Server scheduleServeNext(Event event, Consumer<Event> hasCustomerConsumer, 
        Runnable noCustomer) {
        return new SelfCheckoutServer(
            super.scheduleServeNext(event, hasCustomerConsumer, noCustomer)
            );
    }

    @Override
    String getQueueOwner() {
        return String.format("self-check %d", getQueueOwnerId());
    }

    @Override
    public String toString() {
        return String.format("self-check %d", getId());
    }

    @Override
    boolean isSelfCheckOut() {
        return true;
    }
}
