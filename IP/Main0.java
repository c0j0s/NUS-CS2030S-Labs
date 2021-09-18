import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import cs2030.simulator.Simulator;

class Main0 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Double> arrivalTimes = new ArrayList<Double>();

        while (sc.hasNextDouble()) {
            arrivalTimes.add(sc.nextDouble());
        }
        Simulator s = new Simulator(arrivalTimes);
        s.simulate();
    }
}
