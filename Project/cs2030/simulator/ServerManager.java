package cs2030.simulator;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A server service wrapper class to handle event transitions.
 */
class ServerManager {

    private static final int CUST_SERVE = 0;
    private static final int CUST_LEAVE = 1;

    private final ServerList servers;

    private final Map<Integer, Double> stat;
    private final Map<String, Double> waitStat;

    /**
     * Server manager constructor.
     * @param numOfServer       number of human server
     * @param numOfSelfServer   number of self checkout server
     * @param queueLimit          queue limit
     */
    ServerManager(int numOfServer, int numOfSelfServer, int queueLimit) {
        this.servers = new ServerList(numOfServer, numOfSelfServer, queueLimit);
        stat = new HashMap<Integer, Double>();
        waitStat = new HashMap<String, Double>();
        stat.put(CUST_SERVE, 0d);
        stat.put(CUST_LEAVE, 0d);
    }

    /**
     * Handles arrival event.
     * @param event     arrive event
     * @param consumer  next event handler
     */
    void handleArrive(Event event, BiConsumer<Event, String> consumer) {
        servers.getAvailableServer(event.getCustomer(), server -> {
            return server.scheduleServe(event, newEvent -> {
                String serverString = server.toString();

                if (newEvent.getState() == EventState.LEAVE) {
                    // STATS
                    stat.put(CUST_LEAVE, stat.get(CUST_LEAVE) + 1);
                    serverString = "";
                } 
                if (newEvent.getState() == EventState.WAIT) {
                    // STATS
                    recordWaitTime(true, event.getCustomer(), event.getTime());
                    serverString = server.getQueueOwner();
                }

                consumer.accept(newEvent, newEvent + serverString);
            });
        }, () -> {
                // System.out.println("[DEBUG] handleArrive: no server found.");
            });
    }

    /**
     * Handles serve event.
     * @param event     serve event
     * @param consumer  next event handler
     */
    void handleServe(Event event, BiConsumer<Event, String> consumer) {
        servers.getServerByCustomer(event.getCustomer(), server -> {
            return server.scheduleDone(event, newEvent -> {
                consumer.accept(newEvent, newEvent + server.toString());
            });
        }, () -> {
            // System.out.println("[DEBUG] handleServe: no server/customer found.");
            });
    }

    /**
     * Handles done event.
     * @param event         done event
     * @param getRestTime   rest time generator
     * @param consumer      next event handler
     * @param idleConsumer  idle state handler
     */
    void handleDone(Event event, Supplier<Double> getRestTime, BiConsumer<Event, String> consumer, 
        Consumer<String> idleConsumer) {

        servers.getServerByCustomer(event.getCustomer(), server -> {
            // STATS
            stat.put(CUST_SERVE, stat.get(CUST_SERVE) + 1);

            String log = event + server.toString();
            return server.scheduleRestOrServe(event, getRestTime, newEvent -> {
                if (newEvent.getState() == EventState.SERVE) {
                    // STATS
                    recordWaitTime(false, newEvent.getCustomer(), newEvent.getTime());
                    consumer.accept(newEvent, log + "\n" + newEvent + server);
                } else {
                    consumer.accept(newEvent, log);
                }
            }, () -> {
                    // System.out.println("[DEBUG] handleDone: server has no job in queue.");
                    idleConsumer.accept(log);
                });
        }, () -> {
                // System.out.println("[DEBUG] handleDone: no server/customer found.");
            });
    }

    /**
     * Handles rest event.
     * @param event     rest event
     * @param consumer  next event handler
     */
    void handleWake(Event event, BiConsumer<Event, String> consumer) {
        servers.getServerByCustomer(event.getCustomer(), server -> {
            return server.scheduleServeNext(event, newEvent -> {
                if (newEvent.getState() == EventState.SERVE) {
                    // STATS
                    recordWaitTime(false, newEvent.getCustomer(), newEvent.getTime());
                }

                consumer.accept(newEvent, newEvent + server.toString());
            }, () -> {
                // System.out.println("[DEBUG] handleWake: server has no job in queue.");
                });
        }, () -> {
                // System.out.println("[DEBUG] handleWake: no server/customer found.");
            });
    }

    /**
     * Helper method to record wait time.
     * @param arrive    new customer toggle
     * @param customer  customer
     * @param time      event time
     */
    private void recordWaitTime(boolean arrive, Optional<Customer> customer, double time) {
        if (arrive) {
            waitStat.put(customer.toString(), time);
        } else {
            if (waitStat.containsKey(customer.toString())) {
                waitStat.put(customer.toString(), time - waitStat.get(customer.toString()));
            }
        }
    }

    /**
     * Helper method to print statistics.
     * @return formated statistics
     */
    String getStats() {
        return String.format("[%.3f %.0f %.0f]", 
            waitStat.values().stream().reduce(0d, (x, y) -> x + y) / stat.get(CUST_SERVE), 
            stat.get(CUST_SERVE), 
            stat.get(CUST_LEAVE));
    }
}
