package cs2030.simulator;

import java.util.ArrayList;

class Server implements Comparable<Server> {

    private final int id;
    private final int queue_limit;

    private final ServerState state;
    private final double releaseTiming;
    
    private final ArrayList<Customer> queue;

    Server(int id, int limit) {
        // additional 1 slot for serving
        this(id, limit + 1, 0, ServerState.IDLE,new ArrayList<Customer>());
    }

    /** for updating server next available timing */
    // Server(Server s, double rt) {
    //     this(s.id, s.queue_limit, rt, s.state, s.queue);
    // }

    /** for updating server state */
    Server(Server s, double rt, ServerState state) {
        this(s.id, s.queue_limit, rt, state, s.queue);
    }

    Server(int id, int limit, double rt, ServerState state, ArrayList<Customer> q) {
        this.id = id;
        this.queue_limit = limit;

        this.state = state;
        this.releaseTiming = rt;
        
        this.queue = q;
    }

    int getId() {
        return this.id;
    }

    /**
     * Update server state and release timing
     * @param event
     * @return
     */
    Server serve(Event event) {
        queue.add(event.getCustomer());
        return new Server(this, event.getTime() + event.getCustomer().getServiceTime(), nextState());
    }
    
    Server serveNext(Event event) {
        return new Server(this, this.releaseTiming + event.getCustomer().getServiceTime(), nextState());
    }

    Server wait(Event event) {
        queue.add(event.getCustomer());
        double timing = this.releaseTiming > event.getTime()? this.releaseTiming: event.getTime();
        return new Server(this, timing, nextState());
    }

    /**
     * Update server state
     * @param event
     * @return
     */
    Server done(Event event) {
        queue.remove(event.getCustomer());
        double timing = this.releaseTiming > event.getTime()? this.releaseTiming: event.getTime();
        return new Server(this, timing, nextState());
    }

    double getNextTiming() {
        return this.releaseTiming;
    }

    ServerState nextState() {
        if (queue.size() == 0) {
            return ServerState.IDLE;
        }

        if (queue.size() < this.queue_limit) {
            return ServerState.SERVING;
        } else {
            return ServerState.FULL;
        }
    }


    void notifyDone(Event event) {
        queue.remove(event.getCustomer());
    }

    @Override
    public int compareTo(Server o) {
        // TODO: the test case assigns customer to any server as long as it is available to wait
        // this comparator forces server list to order in terms of its queue size
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

    ServerState getState() {
        return this.state;
    }

}
