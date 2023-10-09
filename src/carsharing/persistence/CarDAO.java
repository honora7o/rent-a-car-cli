package carsharing.persistence;

import carsharing.model.Car;

import java.util.List;

public interface CarDAO {
    List<Car> getAllAvailableCarsFromCompanyID(Integer companyID);
    void addCar(Car car);
    Integer getCarIdByName(String carName);
}
