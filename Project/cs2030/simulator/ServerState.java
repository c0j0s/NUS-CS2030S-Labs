package cs2030.simulator;

enum ServerState {
    IDLE(0),       // server free.
    SERVING(1),    // serving one customer, allow to wait.
    BREAK(1),      // on break, queue allow to wait.
    FULL(2);       // serving one customer, customers in queue, queue full.

    private final int priority;

    private ServerState(int v) { 
        priority = v; 
    }

    int getRank() { 
        return priority; 
    }
}