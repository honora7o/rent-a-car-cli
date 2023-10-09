package carsharing.persistence;

import carsharing.model.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDAOImpl implements CarDAO{
    private final Connection connection;

    public CarDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Car> getAllAvailableCarsFromCompanyID(Integer companyID) {
        List<Car> availableCarsFromCompanyID = new ArrayList<>();

        try {
            Statement stmt = this.connection.createStatement();

            String sql = "SELECT C.* FROM CAR C " +
                    "LEFT JOIN CUSTOMER CU ON C.ID = CU.RENTED_CAR_ID " +
                    "WHERE C.COMPANY_ID = " + companyID +
                    " AND CU.RENTED_CAR_ID IS NULL";

            ResultSet resultSet = stmt.executeQuery(sql);

            while (resultSet.next()) {
                int carID = resultSet.getInt("ID");
                String carName = resultSet.getString("NAME");
                int companyId = resultSet.getInt("COMPANY_ID");

                Car car = new Car(carID, carName, companyId);
                availableCarsFromCompanyID.add(car);
            }

            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return availableCarsFromCompanyID;
    }

    @Override
    public void addCar(Car car) {
        try {
            String sql = "INSERT INTO CAR (NAME, COMPANY_ID) VALUES (?, ?)";
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);

            preparedStatement.setString(1, car.getName());
            preparedStatement.setInt(2, car.getCompanyId());

            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer getCarIdByName(String carName) {
        try {
            String sql = "SELECT ID FROM CAR WHERE NAME = ?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, carName);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("ID");
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
