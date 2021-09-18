package cs2030.simulator;

class Customer implements Comparable<Customer> {
    private final int id;
    private final double waitTime;

    Customer(int id) {
        this(id, 0);
    }

    Customer(int id, double waitTime) {
        this.id = id;
        this.waitTime = waitTime;
    }

    Customer(Customer customer, double waitTime) {
        this(customer.id, waitTime);
    }

    double getWaitTime() {
        return waitTime;
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
