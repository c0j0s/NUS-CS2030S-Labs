class PrivateCar extends Driver {

    PrivateCar(String p, int t) {
        super(p, t, new ShareARide());
    }

    @Override
    public String toString() {
        return super.toString() + " PrivateCar";
    }
}
