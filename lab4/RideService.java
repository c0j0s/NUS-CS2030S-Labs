abstract class RideService {
    private final int base;
    private final int paxRate;
    private final int bookFee;
    private final int peakFee;

    RideService(int b, int pr, int bf, int pf) {
        this.base = b;
        this.paxRate = pr;
        this.bookFee = bf;
        this.peakFee = pf;
    }

    int computeFare(Request r) {
        int b = this.base * r.getDist();

        int pf = 0;
        if (r.getTime() >= 600 && r.getTime() <= 900) {
            pf += peakFee;
        }

        int pr = paxRate;
        if (paxRate != 1) {
            pr = r.getPax();
        }
        
        return (b + bookFee + pf) / pr;
    }

}
