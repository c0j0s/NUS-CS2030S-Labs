class Loader {
    private final int identifier;
    private final Cruise cruise;

    Loader(int identifier, Cruise cruise) {
        this.identifier = identifier;
        this.cruise = cruise;
    }

    boolean canServe(Cruise cruise) {
        return cruise.getArrivalTime() >= this.getNextAvailableTime();
    }

    Loader serve(Cruise cruise) {
        if (this.canServe(cruise)) {
            return new Loader(this.identifier, cruise);
        } else {
            return this;
        }
    }

    int getIdentifier() {
        return this.identifier;
    }

    int getNextAvailableTime() {
        return this.cruise.getServiceCompletionTime();
    }

    @Override
    public String toString() {
        return String.format("Loader %d serving %s", this.identifier, this.cruise);
    }
}
