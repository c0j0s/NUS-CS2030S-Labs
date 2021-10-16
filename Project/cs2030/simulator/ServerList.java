package cs2030.simulator;

import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

class ServerList {
    private static final int DEFAULT_QUEUE_MAX = 0;

    private final PriorityQueue<Server> servers;

    ServerList(int numOfServer) {
        this(numOfServer, DEFAULT_QUEUE_MAX);
    }

    ServerList(int numOfServer, int queueMax) {
        servers = new PriorityQueue<Server>();
        for (int i = 0; i < numOfServer; i++) {
            servers.add(new Server(i + 1, queueMax));
        }
    }

    Server getAvailableServer() throws ServerNotFoundException {
        if (servers.size() > 0) {
            return servers.poll();
        } else {
            throw new ServerNotFoundException("No server available.");
        }
    }

    Server getServerByEvent(Event event) throws ServerNotFoundException {
        List<Server> list = servers.stream().filter(s -> s.hasCustomer(event.getCustomer()))
                .collect(Collectors.toList());
        if (list.size() == 1) {
            servers.remove(list.get(0));
            return list.get(0);
        } else {
            throw new ServerNotFoundException("No server serving event: " + event);
        }
    }

    void returnServer(Server server) {
        servers.offer(server);
    }
}
