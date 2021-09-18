package cs2030.simulator;

import java.util.List;
import java.util.PriorityQueue;

public class Simulator {

    private final PriorityQueue<Event> pq;

    /**
     * Constructor for simulator.
     * @param eventTimes a list of timings to create initial arrival events.
     */
    public Simulator(List<Double> eventTimes) {
        // create the initial arrival events
        pq = new PriorityQueue<Event>();
        for (int i = 0; i < eventTimes.size(); i++) {
            pq.offer(new ArriveEvent(eventTimes.get(i), new Customer(i + 1)));
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
     * @param numOfServer number of server used for serving customer.
     */
    public void simulate(int numOfServer) {
        ServerManager sm = new ServerManager(numOfServer);
        StatsManager st = new StatsManager();

        while (!pq.isEmpty()) {
            // System.out.println(pq);
            Event event = pq.poll();
            System.out.println(event);
            st = st.recordEvent(event);

            switch (event.getState()) {
                case ARRIVE:
                    pq.offer(sm.tryScheduleServe(event));
                    break;
                case WAIT:
                    pq.offer(sm.scheduleServe(event));
                    break;
                case SERVE:
                    pq.offer(sm.scheduleDone(event));
                    break;
                case DONE:
                    sm.notifyDone(event);
                    break;
                case LEAVE:
                    break;
                default:
                    break;
            }
        }

        System.out.println(st.report());
    }
}
