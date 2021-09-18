class RecycledLoader extends Loader {
    private static final int serviceTime = 60;

    RecycledLoader(int identifier, Cruise cruise) {
        super(identifier, cruise);
    }

    @Override
    RecycledLoader serve(Cruise cruise) {
        if (super.canServe(cruise)) {
            return new RecycledLoader(super.getIdentifier(), cruise);
        } else {
            return this;
        }
    }

    @Override
    int getNextAvailableTime() {
        return serviceTime + super.getNextAvailableTime();
    }

    @Override
    public String toString() {
        return "Recycled " + super.toString();
    }
}
