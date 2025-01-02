package com.cjchika.companyms.company;

import com.cjchika.companyms.dto.ReviewMessage;

import java.util.List;

public interface CompanyService {

    List<Company> getAllCompanies();

    boolean updateCompany(Long id, Company com);

    void createCompany(Company company);

    Company getCompanyById(Long id);

    boolean deleteCompanyById(Long id);

    void updateCompanyRating(ReviewMessage reviewMessage);
}
