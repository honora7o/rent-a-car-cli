package carsharing.business;

import carsharing.model.Car;
import carsharing.model.Customer;
import carsharing.persistence.CustomerDAOImpl;

import java.util.List;

public class CustomerService {
    private final CustomerDAOImpl customerDAOImpl;

    public CustomerService(CustomerDAOImpl customerDAOImpl) {
        this.customerDAOImpl = customerDAOImpl;
    }

    public void addCustomer(Customer customer) {
        this.customerDAOImpl.addCustomer(customer);
    }

    public List<Customer> getAllCustomers() {
        return this.customerDAOImpl.getAllCustomers();
    }

    public void rentCar(int customerId, int rentedCarId) {
        this.customerDAOImpl.updateRentedCarId(customerId, rentedCarId);
    }

    public Car getRentedCarForCustomer(int customerId) {
        return this.customerDAOImpl.getRentedCarForCustomer(customerId);
    }

    public void returnRentedCar(int customerId) {
        this.customerDAOImpl.returnRentedCar(customerId);
    }
}
