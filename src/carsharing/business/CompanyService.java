package carsharing.business;

import carsharing.model.Company;
import carsharing.persistence.CompanyDAOImpl;

import java.util.List;

public class CompanyService {
    private final CompanyDAOImpl companyDAOImpl;

    public CompanyService(CompanyDAOImpl companyDAOImpl) {
        this.companyDAOImpl = companyDAOImpl;
    }

    public List<Company> getAllCompanies() {
        return this.companyDAOImpl.getAllCompanies();
    }

    public void addCompany(Company company) {
        this.companyDAOImpl.addCompany(company);
    }

    public Company getCompanyById(int companyId) {
        return this.companyDAOImpl.getCompanyById(companyId);
    }
}
