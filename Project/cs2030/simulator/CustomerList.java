package cs2030.simulator;

import java.util.ArrayList;
import java.util.List;

class CustomerList {
    private final List<Customer> customers;

    CustomerList() {
        customers = new ArrayList<Customer>();
    }

    Customer getServingCustomer() {
        return customers.get(0);
    }
}
