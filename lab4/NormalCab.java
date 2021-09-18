class NormalCab extends Driver {

    NormalCab(String p, int t) {
        super(p, t, new TakeACab());
    }

    @Override
    public String toString() {
        return super.toString() + " NormalCab";
    }
}
