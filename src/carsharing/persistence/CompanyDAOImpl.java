package carsharing.persistence;

import carsharing.model.Company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyDAOImpl implements CompanyDAO {
    private final Connection connection;

    public CompanyDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Company> getAllCompanies() {
        List<Company> companies = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM COMPANY ORDER BY ID");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                companies.add(new Company(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return companies;
    }

    @Override
    public void addCompany(Company company) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO COMPANY (NAME) VALUES (?)")) {
            statement.setString(1, company.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Company getCompanyById(int companyId) {
        try {
            String sql = "SELECT * FROM COMPANY WHERE ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, companyId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");

                return new Company(id, name);
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
