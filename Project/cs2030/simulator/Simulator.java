package cs2030.simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Simulator {
    private static final double DEFAULT_SERVE_TIME = 1.0;
    private final PriorityQueue<Event> pq;

    /**
     * Constructor for simulator with deafult customer servicing time.
     * @param eventTimes a list of timings to create initial arrival events.
     */
    public Simulator(List<Double> eventTimes) {
        // create the initial arrival events
        pq = new PriorityQueue<Event>();
        for (int i = 0; i < eventTimes.size(); i++) {
            pq.offer(new ArriveEvent(eventTimes.get(i), new Customer(i + 1, DEFAULT_SERVE_TIME)));
        }
    }

    /**
     * Constructor for simulator with vary customer servicing time.
     * @param eventTimes a list of timings to create initial arrival events.
     * @param serviceTimes a list of servicing timings of customers.
     */
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
     * Level 1: simulate all events. with a default queue limit of 1.
     * 
     * @param numOfServer number of server used for serving customer.
     */
    public void simulate(int numOfServer) {
        simulate(numOfServer, 1);
    }

    /**
     * Level 2: simulate all events.
     * 
     * @param numOfServer number of server used for serving customer.
     * @param queueMax    customer queue limit.
     */
    public void simulate(int numOfServer, int queueMax) {
        simulate(numOfServer, queueMax, new ArrayList<Double>());
    }

    /**
     * Level 3: simulate all events.
     * 
     * @param numOfServer      number of server used for serving customer.
     * @param queueMax         customer queue limit.
     * @param serverBreakTimes a sequence of resting times a server takes,
     */
    public void simulate(int numOfServer, int queueMax, List<Double> serverBreakTimes) {
        ServerManager sm = new ServerManager(numOfServer, queueMax);
        int restCounter = 0;
        while (!pq.isEmpty()) {
            Event event = pq.poll();
            if (event.getState() != EventState.REST) {
                System.out.println(event);
            }

            // only handles arrive event and done event
            // arrive -> create server and done or wait, serve and done event
            // done -> update server state from busy to serving or idle
            // server takes in an event and output events base on server state
            switch (event.getState()) {
                case ARRIVE:
                    // schedule event, return event based on server status
                    sm.handleArrive(event).ifPresent(e -> pq.add(e));
                    break;
                case SERVE:
                    // schedule done
                    sm.handleServe(event).ifPresent(e -> pq.add(e));
                    break;
                case DONE:
                    // is it time to rest?
                    if (serverBreakTimes.get(restCounter) != 0) {
                        // put server to rest
                        sm.handleDoneAndRest(event, serverBreakTimes.get(restCounter))
                            .ifPresent(e -> pq.add(e));
                    } else {
                        // serve next customer is any
                        sm.handleDone(event).ifPresent(e -> pq.add(e));
                    }
                    restCounter++;
                    break;
                case REST:
                    // wake server
                    sm.handleServerWake(event).ifPresent(e -> pq.add(e));
                    break;
                default:
                    break;
            }
        }

        System.out.println(sm.getStats());
    }

}
