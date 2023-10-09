package carsharing.persistence;

import carsharing.model.Car;
import carsharing.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO{
    private final Connection connection;

    public CustomerDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addCustomer(Customer customer) {
        String insertCustomerSQL = "INSERT INTO CUSTOMER (NAME) VALUES (?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertCustomerSQL)) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String selectCustomersSQL = "SELECT * FROM CUSTOMER ORDER BY ID";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectCustomersSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                int rentedCarId = resultSet.getInt("RENTED_CAR_ID");

                Customer customer = new Customer(id, name, rentedCarId);
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    @Override
    public void updateRentedCarId(int customerId, int rentedCarId) {
        String updateRentedCarIdSQL = "UPDATE CUSTOMER SET RENTED_CAR_ID = ? WHERE ID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateRentedCarIdSQL)) {
            preparedStatement.setInt(1, rentedCarId);
            preparedStatement.setInt(2, customerId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Car getRentedCarForCustomer(int customerId) {
        try {
            String sql = "SELECT C.* FROM CUSTOMER CU " +
                    "INNER JOIN CAR C ON CU.RENTED_CAR_ID = C.ID " +
                    "WHERE CU.ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customerId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int carID = resultSet.getInt("ID");
                String carName = resultSet.getString("NAME");
                int companyId = resultSet.getInt("COMPANY_ID");

                return new Car(carID, carName, companyId);
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void returnRentedCar(int customerId) {
        String returnRentedCarSQL = "UPDATE CUSTOMER SET RENTED_CAR_ID = NULL WHERE ID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(returnRentedCarSQL)) {
            preparedStatement.setInt(1, customerId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
