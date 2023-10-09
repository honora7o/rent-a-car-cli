package carsharing.business;

import carsharing.model.Car;
import carsharing.persistence.CarDAOImpl;

import java.util.List;

public class CarService {
    private final CarDAOImpl carDAOImpl;

    public CarService(CarDAOImpl carDAOImpl) {
        this.carDAOImpl = carDAOImpl;
    }

    public List<Car> getAllAvailableCarsFromCompanyByID(Integer companyID) {
        return this.carDAOImpl.getAllAvailableCarsFromCompanyID(companyID);
    }

    public void addCar(Car car) {
        this.carDAOImpl.addCar(car);
    }

    public Integer getCarIDByName(String carName) {
        return this.carDAOImpl.getCarIdByName(carName);
    }
}
