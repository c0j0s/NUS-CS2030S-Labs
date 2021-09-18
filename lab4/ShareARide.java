class ShareARide extends RideService {

    ShareARide() {
        super(50, 2, 0, 500);
    }

    @Override
    int computeFare(Request r) {
        return super.computeFare(r);
    }

    @Override
    public String toString() {
        return "ShareARide";
    }
}
