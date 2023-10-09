package carsharing.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseManager {
    private Connection connection = null;
    private Statement stmt = null;
    private String jdbcURL = "jdbc:h2:file:../db/";

    public DatabaseManager(String dbFileName) {
        this.jdbcURL = jdbcURL + dbFileName;
    }

    public void startDB() {
        try {
            Class.forName("org.h2.Driver");

            this.connection = DriverManager.getConnection(this.jdbcURL);
            this.connection.setAutoCommit(true);

            stmt = connection.createStatement();
            String createCompanyTableSQL = "CREATE TABLE IF NOT EXISTS COMPANY " +
                    "(ID INT AUTO_INCREMENT PRIMARY KEY, " +
                    " NAME VARCHAR(255) NOT NULL UNIQUE)";
            stmt.executeUpdate(createCompanyTableSQL);

            String createCarTableSQL = "CREATE TABLE IF NOT EXISTS CAR " +
                    "(ID INT AUTO_INCREMENT PRIMARY KEY, " +
                    " NAME VARCHAR(255) NOT NULL UNIQUE, " +
                    " COMPANY_ID INT NOT NULL, " +
                    " FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID))";
            stmt.executeUpdate(createCarTableSQL);

            String createCustomerTableSQL = "CREATE TABLE IF NOT EXISTS CUSTOMER " +
                    "(ID INT AUTO_INCREMENT PRIMARY KEY, " +
                    " NAME VARCHAR(255) NOT NULL UNIQUE, " +
                    " RENTED_CAR_ID INT, " +
                    " FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR(ID))";
            stmt.executeUpdate(createCustomerTableSQL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
