package cs2030.simulator;

class Customer implements Comparable<Customer> {
    private final int id;
    // private final double waitTime;
    private final double serviceTime;

    Customer(int id) {
        this(id, 0);
    }

    Customer(int id, double serviceTime) {
        this.id = id;
        this.serviceTime = serviceTime;
    }

    Customer(Customer customer, double waitTime) {
        this(customer.id, waitTime);
    }

    // double getWaitTime() {
    //     return waitTime;
    // }

    double getServiceTime() {
        return this.serviceTime;
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
