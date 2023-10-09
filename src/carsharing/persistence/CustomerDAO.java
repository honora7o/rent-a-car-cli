package carsharing.persistence;

import carsharing.model.Car;
import carsharing.model.Customer;

import java.util.List;

public interface CustomerDAO {
    void addCustomer(Customer customer);
    List<Customer> getAllCustomers();
    void updateRentedCarId(int customerId, int rentedCarId);
    Car getRentedCarForCustomer(int customerId);
    void returnRentedCar(int customerId);
}
