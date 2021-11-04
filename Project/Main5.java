import java.util.Scanner;

import cs2030.simulator.Simulator;

class Main5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int seed = sc.nextInt();
        int numOfServer = sc.nextInt();
        int numOfSelfServer = sc.nextInt();
        int queueMax = sc.nextInt();
        int numOfCust = sc.nextInt();

        double arrivalRate = sc.nextDouble();
        double serviceRate = sc.nextDouble();
        double restingRate = sc.nextDouble();
        double restingProb = sc.nextDouble();
        double greedyProb = sc.nextDouble();

        Simulator s = new Simulator(numOfCust, seed, arrivalRate, 
            serviceRate, restingRate, restingProb, greedyProb);
        s.simulate(numOfServer, numOfSelfServer, queueMax);
        sc.close();
    }
}
