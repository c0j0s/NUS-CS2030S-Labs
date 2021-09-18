package cs2030.simulator;

import java.util.ArrayList;

class Server implements Comparable<Server> {
    private static final int QUEUE_LIMIT = 2;
    private static final int SERV_TIME = 1;

    private final int id;
    private final double nextTiming;

    private final ArrayList<Customer> queue;

    Server(int id) {
        this(id, 0, new ArrayList<Customer>());
    }

    Server(Server server, double t) {
        this(server.id, t, server.queue);
    }

    Server(int id, double t, ArrayList<Customer> q) {
        this.id = id;
        nextTiming = t;
        queue = q;
    }

    Event tryScheduleServe(Event event) {
        if (queue.isEmpty()) {
            return scheduleServe(event);
        } else if (canWait()) {
            queue.add(event.getCustomer());
            return new WaitEvent(event, this.id);
        }
        return new LeaveEvent(event);
    }

    Event scheduleServe(Event event) {
        double time = this.nextTiming;
        if (!queue.contains(event.getCustomer())) {
            queue.add(event.getCustomer());

        }
        if (time == 0) {
            time = event.getEventTiming();
        }
        if (event.getState() == EventState.WAIT) {
            double wait = time - event.getEventTiming();
            return new ServeEvent(time, new Customer(event.getCustomer(), wait), this.id);
        }
        return new ServeEvent(event, time, this.id);
    }

    double getNextTiming() {
        return this.nextTiming;
    }

    boolean canWait() {
        return (queue.size() < QUEUE_LIMIT);
    }

    Event scheduleDone(Event event) {
        return new DoneEvent(event, this.nextTiming + SERV_TIME, this.id);
    }

    void notifyDone(Event event) {
        queue.remove(event.getCustomer());
    }

    @Override
    public int compareTo(Server o) {
        int diff = this.queue.size() - o.queue.size();
        if (diff == 0) {
            return this.id - o.id;
        }
        return diff;
    }

    @Override
    public String toString() {
        return "server " + id;
    }

    public boolean hasCustomer(Customer customer) {
        return queue.contains(customer);
    }

}
