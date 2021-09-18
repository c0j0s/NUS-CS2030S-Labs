package cs2030.simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

class ServerManager {

    private final PriorityQueue<Server> servers;

    ServerManager(int numOfServer) {
        servers = new PriorityQueue<Server>();
        for (int i = 0; i < numOfServer; i++) {
            servers.add(new Server(i + 1));
        }
    }

    Server getAvailableServer() {
        // System.out.println(servers);
        sortServerList();
        return servers.peek();
    }

    private void sortServerList() {
        Server tmp = servers.poll();
        servers.offer(tmp);
    }

    private void updateServerTiming(Server server, double nextTiming) {
        servers.remove(server);
        servers.offer(new Server(server, nextTiming));
    }

    List<Server> getServerByEvent(Event event) {
        List<Server> list = new ArrayList<Server>();
        for (Server server : servers) {
            if (server.hasCustomer(event.getCustomer())) {
                list.add(server);
                return list;
            }
        }
        
        return list;
    }

    Event tryScheduleServe(Event event) {
        Server server = getAvailableServer();
        Event newEvent = server.tryScheduleServe(event);
        if (newEvent.getState() == EventState.SERVE) {
            updateServerTiming(server, newEvent.getEventTiming());
        }
        return newEvent;
    }

    Event scheduleServe(Event event) {
        List<Server> list = getServerByEvent(event);
        if (list.size() == 1) {
            Server server = list.get(0);
            Event newEvent = server.scheduleServe(event);
            updateServerTiming(server, newEvent.getEventTiming());
            return newEvent; 
        }
        return new LeaveEvent(event);
    }

    Event scheduleDone(Event event) {
        List<Server> list = getServerByEvent(event);
        if (list.size() == 1) {
            Server server = list.get(0);
            Event newEvent = server.scheduleDone(event);
            updateServerTiming(server, newEvent.getEventTiming());
            return newEvent; 
        }
        return new LeaveEvent(event);
    }

    void notifyDone(Event event) {
        List<Server> list = getServerByEvent(event);
        if (list.size() == 1) {
            Server server = list.get(0);
            server.notifyDone(event);
        }
    }

}
