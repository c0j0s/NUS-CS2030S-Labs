package cs2030.simulator;

import java.util.function.Supplier;

/**
 * A customer class.
 */
class Customer implements Comparable<Customer> {
    
    private final int id;
    private final Supplier<Double> serviceTime;

    Customer(int id, double serviceTime) {
        this(id, () -> serviceTime);
    }

    Customer(int id, Supplier<Double> serviceTime) {
        this.id = id;
        this.serviceTime = serviceTime;
    }

    double getServiceTime() {
        return this.serviceTime.get();
    }

    boolean isGreedy() {
        return false;
    }

    @Override
    public String toString() {
        return Integer.toString(id);
    }

    @Override
    public int compareTo(Customer customer) {
        return this.id - customer.id;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else {
            return this.id == ((Customer) object).id;
        }     
    }
}
