package cs2030.simulator;

import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A human server.
 */
class HumanServer extends Server {

    HumanServer(int id, ServerState state, double nextTiming, 
        Optional<Customer> servingCustomer, int queueLimit,
        LinkedList<Customer> queue, int queueOwnerId) {
        super(id, state, nextTiming, queueLimit, servingCustomer, queue, queueOwnerId);
    }

    // new server with a new state, customer and time
    HumanServer(HumanServer humanServer, ServerState state, 
        double newTiming, Optional<Customer> servingCustomer) {
        this(humanServer.getId(), state, newTiming, servingCustomer, 
            humanServer.getQueueLimit(), humanServer.getQueue(), 
            humanServer.getQueueOwnerId());
    }

    // new server with a new state and time
    HumanServer(HumanServer humanServer, ServerState state, double newTiming) {
        this(humanServer, state, newTiming, humanServer.getServingCustomer());
    }

    // initial creation
    HumanServer(int id, int queueLimit) {
        this(id, ServerState.IDLE, 0d, Optional.<Customer>empty(), queueLimit, 
            new LinkedList<Customer>(), id);
    }

    @Override
    Server scheduleServe(Event event, Consumer<Event> consumer) {
        double newTiming = getNextTiming();

        if (getState() == ServerState.IDLE) {
            // return serve event
            consumer.accept(new ServeEvent(event));
            // return server
            return new HumanServer(this, determineNextState(false), newTiming, event.getCustomer());
        }

        if (getState() == ServerState.SERVING || getState() == ServerState.BREAK) {
            if (queueCanWait()) {
                // queue customer
                event.getCustomer().ifPresent(customer -> getQueue().add(customer));
                // return wait event
                consumer.accept(new WaitEvent(event));

                // return server
                if (getState() == ServerState.BREAK) {
                    return new HumanServer(this, ServerState.BREAK, newTiming);
                }
                return new HumanServer(this, determineNextState(false), newTiming);
            }
        }

        //FULL
        consumer.accept(new LeaveEvent(event));
        return new HumanServer(this, determineNextState(false), newTiming);
    }

    @Override
    Server scheduleDone(Event event, Consumer<Event> consumer) {
        return event.getCustomer().map(customer -> {
            double nextTiming = event.getTime() + customer.getServiceTime();
            consumer.accept(new DoneEvent(event, nextTiming));
            return new HumanServer(this, determineNextState(false), nextTiming);
        }).orElse(this);
    }

    @Override
    Server scheduleRestOrServe(Event event, Supplier<Double> getRestTime, 
        Consumer<Event> hasCustomerConsumer, Runnable noCustomer) {

        double rest = getRestTime.get();
        if (rest != 0) {
            // System.out.println("[DEBUG] Server resting: " + rest);

            //generate a dummy customer
            Customer customer = new Customer(0 - getId(), () -> 0d);
            Event newEvent = new RestEvent(event.getTime() + rest, Optional.of(customer));
            hasCustomerConsumer.accept(newEvent);
            return new HumanServer(this, ServerState.BREAK, event.getTime() + rest, 
                                    Optional.<Customer>of(customer));
        } else {
            return scheduleServeNext(event, hasCustomerConsumer, noCustomer);
        }
    }

    @Override
    Server scheduleServeNext(Event event, Consumer<Event> hasCustomerConsumer, 
        Runnable noCustomer) {
            
        double nextTiming = event.getTime();

        return getNextCustomer(customer -> {
            hasCustomerConsumer.accept(new ServeEvent(event, Optional.of(customer)));
            return new HumanServer(this, determineNextState(false), nextTiming, 
                                    Optional.<Customer>of(customer));
        }, () -> {
                noCustomer.run();
                return new HumanServer(this, determineNextState(true), nextTiming, 
                                        Optional.<Customer>empty());
            });
    }

    <R> R getNextCustomer(Function<Customer, R> hasCustomerConsumer, Supplier<R> noCustomer) {
        if (getQueue().size() > 0) {
            return hasCustomerConsumer.apply(getQueue().poll());
        }
        return noCustomer.get();
    }

    @Override
    boolean isSelfCheckOut() {
        return false;
    }
}
