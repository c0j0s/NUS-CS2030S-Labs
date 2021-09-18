class Booking implements Comparable<Booking> {
    private final Driver d;
    private final Request r;
    private final RideService rs;
    private final int cost;

    Booking(Driver d, Request r) {
        this.d = d;
        this.r = r;
        this.rs = d.getService(r);
        this.cost = rs.computeFare(r);
    }

    @Override
    public int compareTo(Booking b) {
        int x = this.cost - b.cost;
        if (x == 0) {
            return this.d.getWaitTime() - b.d.getWaitTime();
        }

        return x;
    }

    String toDollarString() {
        return cost / 100 + "." + cost % 100;
    }

    @Override
    public String toString() {
        return String.format("$%s using %s (%s)", toDollarString(), d, rs);
    }
}
