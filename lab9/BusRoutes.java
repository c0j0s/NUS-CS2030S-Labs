import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Encapsulates the result of a query: for a bus stop and a search string, it
 * stores a map of bus services that servce stops with matching name. e.g., stop
 * 12345, name "MRT", with map contains: 96: 11223 "Clementi MRT" 95: 22334
 * "Beuno Vista MRT"
 *
 * @author: Ooi Wei Tsang
 * @version: CS2030 AY19/20 Semester 1, Lab 10
 */
class BusRoutes {
    final BusStop stop;
    final String name;
    final Map<BusService, CompletableFuture<Set<BusStop>>> services;

    /**
     * Constructor for creating a bus route.
     * 
     * @param stop     The first bus stop.
     * @param name     The second bus stop.
     * @param services The set of bus services between the two stops.
     */
    BusRoutes(BusStop stop, String name, 
        Map<BusService, CompletableFuture<Set<BusStop>>> services) {
        this.stop = stop;
        this.name = name;
        this.services = services;
    }

    /**
     * Return a string describing the bus route.
     * 
     * @return The first line contains the query information: bus stop id and search
     *         string. The remaining line contains the bus services and matching
     *         stops served.
     */
    public CompletableFuture<String> description() {
        CompletableFuture<String> result = CompletableFuture
            .completedFuture("Search for: " + this.stop + " <-> " + name 
                                + ":\n" + "From " + this.stop + "\n");

        List<CompletableFuture<String>> resultList = services.keySet().stream().map(service -> {
            return services.get(service).thenApply(stops -> {
                return describeService(service, stops).join();
            });
        }).collect(Collectors.toList());

        resultList.add(0, result);

        return CompletableFuture.allOf(
                resultList.toArray(new CompletableFuture<?>[0])
            ).thenApply(v -> {
                return resultList.stream().map(cf -> cf.join()).collect(Collectors.joining(""));
            });
    }

    /**
     * Return a string representation a bus service and its matching stops.
     * 
     * @return The first line contains the query information: bus stop id and search
     *         string. The remaining line contains the bus services and matching
     *         stops served.
     */
    public CompletableFuture<String> describeService(BusService service, Set<BusStop> stops) {
        if (stops.isEmpty()) {
            return CompletableFuture.completedFuture("");
        }
        return CompletableFuture.completedFuture(stops.stream().filter(stop -> stop != this.stop)
                    .reduce("- Can take " + service + " to:\n",
                        (str, stop) -> str += "  - " + stop + "\n", 
                        (str1, str2) -> str1 + str2));
    }
}
