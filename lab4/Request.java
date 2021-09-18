class Request {
    private final int dist;
    private final int pax;
    private final int time;

    Request(int d, int p, int t) {
        this.dist = d;
        this.pax = p;
        this.time = t;
    }

    int getDist() {
        return this.dist;
    }

    int getTime() {
        return this.time;
    }

    int getPax() {
        return this.pax;
    }

    @Override
    public String toString() {
        return String.format("%dkm for %dpax @ %dhrs", dist, pax, time);
    }
}
