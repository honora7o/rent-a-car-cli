package carsharing.persistence;

import carsharing.model.Company;

import java.util.List;

public interface CompanyDAO {
    List<Company> getAllCompanies();
    void addCompany(Company company);
    Company getCompanyById(int companyId);
}
