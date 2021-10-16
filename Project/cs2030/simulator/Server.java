package cs2030.simulator;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;

class Server implements Comparable<Server> {

    private final int id;
    private final int queueLimit;

    private final ServerState state;
    private final double releaseTiming;

    private final ArrayList<Entry<Double, Customer>> queue;

    Server(int id, int limit) {
        // additional 1 slot for serving
        this(id, limit + 1, 0, ServerState.IDLE, new ArrayList<Entry<Double, Customer>>());
    }

    Server(Server s, double rt, ServerState state) {
        this(s.id, s.queueLimit, rt, state, s.queue);
    }

    Server(Server s, ServerState state) {
        this(s.id, s.queueLimit, s.releaseTiming, state, s.queue);
    }

    Server(int id, int limit, double rt, ServerState state, ArrayList<Entry<Double, Customer>> q) {
        this.id = id;
        this.queueLimit = limit;

        this.state = state;
        this.releaseTiming = rt;

        this.queue = q;
    }

    int getId() {
        return this.id;
    }

    ServerState getState() {
        return this.state;
    }

    double getNextTiming() {
        return this.releaseTiming;
    }

    Double getCustomerWaitTime() {
        if (queue.size() > 0) {
            return this.getNextTiming() - queue.get(0).getKey();
        }
        return 0d;
    }

    Optional<Customer> getNextCustomer() {
        if (queue.size() > 0) {
            return Optional.<Customer>of(queue.get(0).getValue());
        }
        return Optional.empty();
    }

    boolean hasCustomer(Customer customer) {
        for (Entry<Double, Customer> entry : queue) {
            if (entry.getValue().equals(customer)) {
                return true;
            }
        }
        return false;
    }

    Server serve(Event event) {
        queue.add(Map.entry(event.getTime(), event.getCustomer()));
        return new Server(this, nextState());
    }

    Server releaseCustomer(Event event) {
        if (queue.size() > 0) {
            queue.remove(0);
        }
        return new Server(this, nextState());
    }

    Server wait(Event event) {
        queue.add(Map.entry(event.getTime(), event.getCustomer()));
        return new Server(this, nextState());
    }

    Server done(Event event) {
        return new Server(this, 
                    event.getTime() + event.getCustomer().getServiceTime(), 
                    nextState());
    }

    Server wake(Event event) {
        queue.removeIf(e -> e.getValue().equals(event.getCustomer()));
        return new Server(this, event.getTime(), nextState());
    }

    Server rest(Double duration, Customer dummy) {
        queue.add(Map.entry(duration, dummy));
        return new Server(this, this.releaseTiming + duration, ServerState.BREAK);
    }

    private ServerState nextState() {
        if (queue.size() == 0) {
            return ServerState.IDLE;
        }

        if (queue.size() < this.queueLimit) {
            return ServerState.SERVING;
        } else {
            return ServerState.FULL;
        }
    }

    @Override
    public int compareTo(Server o) {
        // rank by state: lower state ordinal -> higher priority
        int d = this.state.ordinal() - o.state.ordinal();
        if (d != 0) {
            return d;
        }

        // rank by server id
        d = this.id - o.id;
        if (d != 0) {
            return d;
        }

        // rank by queue size
        return this.queue.size() - o.queue.size();
    }

    @Override
    public String toString() {
        return "server " + id;
    }

}
