package carsharing;

import carsharing.business.CarService;
import carsharing.business.CompanyService;
import carsharing.business.CustomerService;
import carsharing.persistence.CarDAOImpl;
import carsharing.persistence.CompanyDAOImpl;
import carsharing.database.DatabaseManager;
import carsharing.persistence.CustomerDAOImpl;
import carsharing.presentation.UserMenu;

import java.sql.Connection;

public class Main {

    public static void main(String[] args) {
        String dbFileName = "default_database_name";

        if (args.length > 0 && args[0].equals("-databaseFileName")) {
            if (args.length > 1) {
                dbFileName = args[1];
            }
        }

        DatabaseManager dbManager = new DatabaseManager(dbFileName);
        dbManager.startDB();
        Connection connection = dbManager.getConnection();

        UserMenu userMenu = getUserMenu(connection);

        userMenu.startUI();
    }

    private static UserMenu getUserMenu(Connection connection) {
        CompanyDAOImpl companyDAOImpl = new CompanyDAOImpl(connection);
        CompanyService companyService = new CompanyService(companyDAOImpl);

        CarDAOImpl carDAOImpl = new CarDAOImpl(connection);
        CarService carService = new CarService(carDAOImpl);

        CustomerDAOImpl customerDAOImpl = new CustomerDAOImpl(connection);
        CustomerService customerService = new CustomerService(customerDAOImpl);

        UserMenu userMenu = new UserMenu(companyService, carService, customerService);
        return userMenu;
    }
}