package homework;

import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class CustomerService {

    NavigableMap<Customer, String> customerMap = new TreeMap<>(Comparator.comparing(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> smallestEntry = customerMap.firstEntry();
        if (smallestEntry == null) {
            return null;
        } else {
            return new Map.Entry<>() {
                @Override
                public Customer getKey() {
                    Customer innerCustomer = smallestEntry.getKey();
                    return new Customer(innerCustomer.getId(), innerCustomer.getName(), innerCustomer.getScores());
                }

                @Override
                public String getValue() {
                    return smallestEntry.getValue();
                }

                @Override
                public String setValue(String value) {
                    return "";
                }
            };
        }
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> nextEntry = customerMap.higherEntry(customer);
        if (nextEntry == null) {
            return null;
        } else {
            return new Map.Entry<>() {
                @Override
                public Customer getKey() {
                    Customer innerCustomer = nextEntry.getKey();
                    return new Customer(innerCustomer.getId(), innerCustomer.getName(), innerCustomer.getScores());
                }

                @Override
                public String getValue() {
                    return nextEntry.getValue();
                }

                @Override
                public String setValue(String value) {
                    return "";
                }
            };
        }
    }

    public void add(Customer customer, String data) {
        customerMap.put(customer, data);
    }
}
