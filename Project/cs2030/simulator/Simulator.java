package cs2030.simulator;

import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.Supplier;

/**
 * Main simulator class to simulate events.
 */
public class Simulator {
    private static final double DEFAULT_SERVE_TIME = 1.0;
    private final PriorityQueue<Event> pq;

    private final Supplier<Double> getRestTime;

    /**
     * Constructor for simulator with deafult customer servicing time.
     * @param eventTimes a list of timings to create initial arrival events.
     */
    public Simulator(List<Double> eventTimes) {
        // create the initial arrival events
        pq = new PriorityQueue<Event>();
        for (int i = 0; i < eventTimes.size(); i++) {
            pq.offer(new ArriveEvent(
                eventTimes.get(i), 
                new Customer(i + 1, () -> DEFAULT_SERVE_TIME)
            ));
        }

        getRestTime = () -> 0d;
    }

    /**
     * Constructor for simulator with vary customer servicing time.
     * @param eventTimes   a list of timings to create initial arrival events.
     * @param serviceTimes a list of servicing timings of customers.
     */
    public Simulator(List<Double> eventTimes, List<Double> serviceTimes, 
        List<Double> serverBreakTimes) {

        // create the initial arrival events
        pq = new PriorityQueue<Event>();
        for (int i = 0; i < eventTimes.size(); i++) {
            pq.offer(new ArriveEvent(eventTimes.get(i), new Customer(i + 1, serviceTimes.get(i))));
        }

        if (serverBreakTimes.size() > 0) {
            Iterator<Double> iterator = serverBreakTimes.iterator();
            getRestTime = () -> iterator.next();
        } else {
            getRestTime = () -> 0d;
        }

    }

    /**
     * Constructor for simulator with configs for random generator.
     * @param numOfCust    number of customer
     * @param seed         an int value denoting the base seed for the RandomGenerator object
     * @param arrivalRate  a positive double parameter for the arrival rate, λ
     * @param serviceRate  a positive double parameter for the service rate, μ
     * @param restingRate  a positive double parameter for the resting rate, ρ
     * @param restingProb  a double parameter for the probability of resting, Pr
     * @param greedyProb   a double parameter for the probability of a greedy customer occurring, Pg
     */
    public Simulator(int numOfCust, int seed, double arrivalRate, double serviceRate, 
        double restingRate, double restingProb, double greedyProb) {

        RandomGenerator generator = new RandomGenerator(seed, arrivalRate, 
                                                        serviceRate, restingRate);
        pq = new PriorityQueue<Event>();

        double arrivalTime = 0;
        for (int i = 0; i < numOfCust; i++) {
            double greedy = generator.genCustomerType();
            Customer newCust;
            if (greedy < greedyProb) {
                newCust = new GreedyCustomer(i + 1, () -> generator.genServiceTime());
            } else {
                newCust = new Customer(i + 1, () -> generator.genServiceTime());
            }
            pq.offer(new ArriveEvent(arrivalTime, newCust));
            arrivalTime += generator.genInterArrivalTime();
        }

        getRestTime = () -> {
            double rest = generator.genRandomRest();
            if (rest < restingProb) {
                return generator.genRestPeriod();
            } else {
                return 0d;
            }
        };

    }

    /**
     * Simulate events. 
     * ARRIVE → SERVE → DONE. 
     * ARRIVE → WAIT → SERVE → DONE. 
     * ARRIVE → LEAVE.
     * @param numOfServer       number of human server
     * @param numOfSelfServer   number of self checkout server
     * @param queueLimit          queue limit
     */
    public void simulate(int numOfServer, int numOfSelfServer, int queueLimit) {
        ServerManager sm = new ServerManager(numOfServer, numOfSelfServer, queueLimit);

        while (!pq.isEmpty()) {
            Event event = pq.poll();
            if (event.getState() == EventState.ARRIVE) {
                System.out.println(event);
            }

            switch (event.getState()) {
                case ARRIVE:
                    sm.handleArrive(event, (newEvent, log) -> {
                        System.out.println(log);
                        pq.add(newEvent);
                    });
                    break;
                case SERVE:
                    sm.handleServe(event, (newEvent, log) -> {
                        pq.add(newEvent);
                    });
                    break;
                case DONE:
                    sm.handleDone(event, getRestTime, 
                        (newEvent, log) -> {
                            System.out.println(log);
                            pq.add(newEvent);
                        }, 
                        log -> {
                            System.out.println(log);
                        });
                    break;
                case REST:
                    sm.handleWake(event, (newEvent, log) -> {
                        System.out.println(log);
                        pq.add(newEvent);
                    });
                    break;
                default:
                    break;
            }
        }

        System.out.println(sm.getStats());
    }

}
