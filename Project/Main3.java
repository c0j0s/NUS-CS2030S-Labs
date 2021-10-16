import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import cs2030.simulator.Simulator;

class Main3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Double> arrivalTimes = new ArrayList<Double>();
        List<Double> serviceTimes = new ArrayList<Double>();
        List<Double> serverBreakTimes = new ArrayList<Double>();

        int numOfServer = sc.nextInt();
        int queueMax = sc.nextInt();
        int numOfCust = sc.nextInt();

        for (int i = 0; i < numOfCust; i++) {
            arrivalTimes.add(sc.nextDouble());
            serviceTimes.add(sc.nextDouble());
        }

        for (int i = 0; i < numOfCust; i++) {
            serverBreakTimes.add(sc.nextDouble());
        }

        Simulator s = new Simulator(arrivalTimes, serviceTimes);
        s.simulate(numOfServer, queueMax, serverBreakTimes);
        sc.close();
    }
}
