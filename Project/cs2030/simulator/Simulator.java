package cs2030.simulator;

import java.util.List;
import java.util.PriorityQueue;

public class Simulator {

    private final PriorityQueue<Event> pq;

    /**
     * Constructor for simulator.
     * 
     * @param eventTimes a list of timings to create initial arrival events.
     */
    public Simulator(List<Double> eventTimes) {
        // create the initial arrival events
        pq = new PriorityQueue<Event>();
        for (int i = 0; i < eventTimes.size(); i++) {
            pq.offer(new ArriveEvent(eventTimes.get(i), new Customer(i + 1, 1.0)));
        }
    }

    public Simulator(List<Double> eventTimes, List<Double> serviceTimes) {
        // create the initial arrival events
        pq = new PriorityQueue<Event>();
        for (int i = 0; i < eventTimes.size(); i++) {
            pq.offer(new ArriveEvent(eventTimes.get(i), new Customer(i + 1, serviceTimes.get(i))));
        }
    }

    /**
     * Level 0: simply outputs all events.
     */
    public void simulate() {
        while (!pq.isEmpty()) {
            Event event = pq.poll();
            System.out.println(event);
        }
    }

    /**
     * Level 1: simulate all events.
     * with a default queue limit of 1
     * @param numOfServer number of server used for serving customer.
     */
    public void simulate(int numOfServer) {
        simulate(numOfServer, 1);
    }

    /**
     * Level 2: simulate all events.
     * 
     * @param numOfServer number of server used for serving customer.
     * @param queueMax customer queue limit
     */
    public void simulate(int numOfServer, int queueMax) {
        ServerManager sm = new ServerManager(numOfServer, queueMax);

        while (!pq.isEmpty()) {
            Event event = pq.poll();
            System.out.println(event);
            
            // only handles arrive event and done event
            // arrive -> create server and done or wait, serve and done event
            // done -> update server state from busy to serving or idle
            // server takes in an event and output events base on server state
            switch (event.getState()) {
                case ARRIVE:
                    pq.addAll(sm.handleEvent(event));
                    break;
                case DONE:
                    sm.notifyDone(event);
                    break;
                default:
                    break;
            }
        }

        System.out.println(sm.getStats());
    }

}
