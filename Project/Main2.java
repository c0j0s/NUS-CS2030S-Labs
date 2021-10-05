import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import cs2030.simulator.Simulator;

class Main2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Double> arrivalTimes = new ArrayList<Double>();
        List<Double> serviceTimes = new ArrayList<Double>();

        int numOfServer = sc.nextInt();
        int queueMax = sc.nextInt();

        while (sc.hasNextDouble()) {
            arrivalTimes.add(sc.nextDouble());
            serviceTimes.add(sc.nextDouble());
        }

        Simulator s = new Simulator(arrivalTimes, serviceTimes);
        s.simulate(numOfServer, queueMax);
        sc.close();
    }
}
