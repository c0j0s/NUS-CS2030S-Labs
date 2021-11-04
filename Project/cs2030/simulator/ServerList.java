package cs2030.simulator;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.function.Function;

/**
 * A server list wrapper class to handle server state changes internally.
 */
class ServerList {

    private final PriorityQueue<Server> servers;

    /**
     * Constructor for server list.
     * @param numOfServer       number of human server
     * @param numOfSelfServer   number of self checkout server
     * @param queueLimit        queue limit
     */
    ServerList(int numOfServer, int numOfSelfServer, int queueLimit) {
        servers = new PriorityQueue<Server>();

        // create human server
        for (int i = 0; i < numOfServer; i++) {
            servers.add(new HumanServer(i + 1, queueLimit));
        }

        // create self checkout server
        if (numOfSelfServer > 0) {
            LinkedList<Customer> masterQueue = new LinkedList<Customer>();
            for (int i = numOfServer; i < numOfServer + numOfSelfServer; i++) {
                Server newServer = new SelfCheckoutServer(i + 1, queueLimit, masterQueue, 
                                                            numOfServer + 1);
                servers.add(newServer);
            }
        } 
    }

    /**
     * Get most suitable server.
     * Handles server state change internally.
     * @param customer      incoming customer
     * @param foundServer   handler to handle if server found
     * @param noServer      handler to handle no server found
     */
    void getAvailableServer(Optional<Customer> customer, Function<Server, Server> foundServer, 
        Runnable noServer) {
        
        customer.ifPresent(c -> {
            if (c.isGreedy()) {

                PriorityQueue<Server> greedyList = new PriorityQueue<Server>(getGreedyComparator());
                greedyList.addAll(servers);

                if (greedyList.size() > 0) {
                    Server chosenServer = greedyList.poll();
                    servers.remove(chosenServer);
                    servers.add(foundServer.apply(chosenServer));
                } else {
                    noServer.run();
                }

            } else {

                if (servers.size() > 0) {
                    // push out a server, get a server back
                    servers.add(foundServer.apply(servers.poll()));
                } else {
                    noServer.run();
                }

            }
        });
    }

    /**
     * Get the server that is handling the customer.
     * @param customer      matching customer
     * @param foundServer   handler to handle if server found
     * @param noServer      handler to handle no server found
     */
    void getServerByCustomer(Optional<Customer> customer, Function<Server, Server> foundServer, 
        Runnable noServer) {
        
        customer.ifPresentOrElse(c -> {
            servers.stream().filter(server -> server.hasCustomer(c)).findFirst()
                .ifPresentOrElse(server -> {
                    servers.remove(server);
                    servers.add(foundServer.apply(server));
                }, noServer);
        }, noServer);
    }

    /**
     * Create a comparator for greedy customer.
     * @return a comparator
     */
    private Comparator<Server> getGreedyComparator() {
        return (s1, s2) -> {
            // rank by state: lower state ordinal -> higher priority
            int d = s1.getState().getRank() - s2.getState().getRank();
            if (d != 0) {
                return d;
            }
            
            // rank by queue size
            d = s1.getQueue().size() - s2.getQueue().size();
            if (d != 0) {
                return d;
            }

            // rank by server id
            return s1.getId() - s2.getId();
        };
    }
}
