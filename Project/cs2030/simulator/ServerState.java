package cs2030.simulator;

enum ServerState {
    IDLE,       // not serving,
    SERVING,    // serving one customer, allow to wait.
    FULL,       // serving one customer, customers in queue, queue full.
}