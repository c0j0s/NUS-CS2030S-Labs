package cs2030.simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

class ServerManager {

    private final PriorityQueue<Server> servers;
    StatsManager st;

    ServerManager(int numOfServer) {
        this(numOfServer, 1);
    }

    ServerManager(int numOfServer, int queueMax) {
        servers = new PriorityQueue<Server>();
        for (int i = 0; i < numOfServer; i++) {
            servers.add(new Server(i + 1, queueMax));
        }
        st = new StatsManager();
    }

    String getStats() {
        return st.toString();
    }

    Server getAvailableServer() {
        return servers.poll();
    }

    List<Server> getServerByEvent(Event event) {
        List<Server> list = new ArrayList<Server>();
        for (Server server : servers) {
            if (server.hasCustomer(event.getCustomer())) {
                servers.remove(server);
                list.add(server);
                return list;
            }
        }

        return list;
    }

    /**
     * To update server state when done with a customer
     * @param event
     */
    void notifyDone(Event event) {
        List<Server> list = getServerByEvent(event);
        if (list.size() == 1) {
            Server server = list.get(0);
            server = server.done(event);
            servers.offer(server);
        }
    }

    /**
     * Handles events base on the avaiable server state
     * @param event
     * @return
     */
    List<Event> handleEvent(Event event) {
        List<Event> results = new ArrayList<Event>();
        Server server = this.getAvailableServer();

        switch (server.getState()) {
            case IDLE:
                // schedule a serve
                results.add(new ServeEvent(event, event.getTime(), server.getId()));
                server = server.serve(event);

                // schedule a done event
                results.add(new DoneEvent(event, server.getNextTiming(), server.getId()));
                break;
            case SERVING:
                // schedule a wait event with default event timing
                results.add(new WaitEvent(event, server.getId()));
                server = server.wait(event);

                // schedule a next serve event with the next server available timing
                results.add(new ServeEvent(event, server.getNextTiming(), server.getId()));
                server = server.serveNext(event);

                // schedule a done event base on the new server timing
                results.add(new DoneEvent(event, server.getNextTiming(), server.getId()));
                break;
            case FULL:
                // schedule a leave event
                results.add(new LeaveEvent(event));
            default:
                break;
        }

        // return server to pool 
        servers.offer(server);

        // TODO: stat manager is not working 
        st = st.recordEvents(results);

        // return simulated results
        return results;
    }

}
