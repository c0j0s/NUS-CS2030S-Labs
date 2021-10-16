package cs2030.simulator;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class ServerManager {

    // private final List<Server> servers;
    // private final List<Double> serverBreakTimes;

    private static final int DEFAULT_QUEUE_MAX = 1;
    private static final int CUST_SERVE = 0;
    private static final int CUST_LEAVE = 1;
    private static final int CUST_WAIT = 2;

    private final ServerList servers;

    // mutable map for stat collection
    private final Map<Integer, Double> stat;

    ServerManager(int numOfServer) {
        this(numOfServer, DEFAULT_QUEUE_MAX);
    }

    /**
     * Base constructor for ServerManager.
     * Init stat with default values.
     * @param numOfServer number of server
     * @param queueMax server queue length limit
     */
    ServerManager(int numOfServer, int queueMax) {
        this.servers = new ServerList(numOfServer, queueMax);
        stat = new HashMap<Integer, Double>();
        stat.put(CUST_SERVE, 0d);
        stat.put(CUST_LEAVE, 0d);
        stat.put(CUST_WAIT, 0d);
    }

    String getStats() {
        return String.format("[%.3f %.0f %.0f]", 
            stat.get(CUST_WAIT) / stat.get(CUST_SERVE), 
            stat.get(CUST_SERVE), 
            stat.get(CUST_LEAVE));
    }

    Optional<Event> handleArrive(Event event) {
        try {
            Server server = servers.getAvailableServer();
            Event output = event;

            switch (server.getState()) {
                case IDLE:
                    output = new ServeEvent(event, server.getId());
                    server = server.serve(event);
                    break;
                case SERVING:
                    output = new WaitEvent(event, server.getId());
                    server = server.wait(event);
                    break;
                default: // FULL or BREAK
                    stat.put(CUST_LEAVE, stat.get(CUST_LEAVE) + 1);
                    output = new LeaveEvent(event);
                    break;
            }

            servers.returnServer(server);
            return Optional.of(output);
        } catch (ServerNotFoundException e) {
            return Optional.empty();
        }
    }

    Optional<Event> handleServe(Event event) {
        try {
            Server server = servers.getServerByEvent(event);
            server = server.done(event);
            servers.returnServer(server);
            return Optional.of(new DoneEvent(event, server.getNextTiming(), server.getId()));
        } catch (ServerNotFoundException e) {
            return Optional.empty();
        }
    }

    Optional<Event> handleDone(Event event) {
        try {
            // record Customer Served
            stat.put(CUST_SERVE, stat.get(CUST_SERVE) + 1);

            Server server = servers.getServerByEvent(event);
            server = server.releaseCustomer(event);
            servers.returnServer(server);
            final int serverId = server.getId();

            // record wait time
            stat.put(CUST_WAIT, stat.get(CUST_WAIT) + (server.getCustomerWaitTime()));

            return server.getNextCustomer().map(c -> new ServeEvent(event, c, serverId));
        } catch (ServerNotFoundException e) {
            return Optional.empty();
        }
    }

    Optional<Event> handleDoneAndRest(Event event, Double duration) {
        try {
            // record Customer Served
            stat.put(CUST_SERVE, stat.get(CUST_SERVE) + 1);
            Server server = servers.getServerByEvent(event);
            Customer dummy = new Customer(0 - server.getId());
            server = server.releaseCustomer(event).rest(duration, dummy);
            servers.returnServer(server);

            return Optional.of(new RestEvent(server.getNextTiming(), dummy, server.getId()));
        } catch (ServerNotFoundException e) {
            return Optional.empty();
        }
    }

    Optional<Event> handleServerWake(Event event) {
        try {
            Server server = servers.getServerByEvent(event);
            server = server.wake(event);
            servers.returnServer(server);
            final int serverId = server.getId();

            // record wait time
            stat.put(CUST_WAIT, stat.get(CUST_WAIT) + (server.getCustomerWaitTime()));

            return server.getNextCustomer().map(c -> new ServeEvent(event, c, serverId));
        } catch (ServerNotFoundException e) {
            return Optional.empty();
        }
    }

}
