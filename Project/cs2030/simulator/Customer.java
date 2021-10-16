package cs2030.simulator;

class Customer implements Comparable<Customer> {
    private final static int DEFAULT_SERVICE_TIME = 0;

    private final int id;
    private final double serviceTime;

    Customer(int id) {
        this(id, DEFAULT_SERVICE_TIME);
    }

    Customer(int id, double serviceTime) {
        this.id = id;
        this.serviceTime = serviceTime;
    }

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
