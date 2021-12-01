package cs2030.simulator;

import java.util.function.Supplier;

class GreedyCustomer extends Customer {

    GreedyCustomer(int id, Supplier<Double> serviceTime)  {
        super(id, serviceTime);
    }

    boolean isGreedy() {
        return true;
    }
    
    @Override
    public String toString() {
        return super.toString() + "(greedy)";
    }
}
