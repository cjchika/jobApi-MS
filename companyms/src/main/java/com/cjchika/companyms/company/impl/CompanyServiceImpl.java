package com.cjchika.companyms.company.impl;

import com.cjchika.companyms.company.Company;
import com.cjchika.companyms.company.CompanyRepository;
import com.cjchika.companyms.company.CompanyService;
import com.cjchika.companyms.company.dto.ReviewMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepo;

    @Override
    public List<Company> getAllCompanies() {
        return companyRepo.findAll();
    }

    @Override
    public boolean updateCompany(Long id, Company company) {
        Optional<Company> companyOptional = companyRepo.findById(id);

        if(companyOptional.isPresent()) {
            Company existingCompany = companyOptional.get();

            existingCompany.setName(company.getName());
            existingCompany.setDescription(company.getDescription());
//            existingCompany.setJobs(company.getJobs());
//            existingCompany.setReviews(company.getReviews());

            companyRepo.save(existingCompany);
            return true;
        }
        return false;
    }

    @Override
    public void createCompany(Company company) {
        companyRepo.save(company);
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepo.findById(id).orElse(null);
    }

    @Override
    public boolean deleteCompanyById(Long id) {

        Optional<Company> companyOptional = companyRepo.findById(id);

        if (companyOptional.isPresent()) {
            companyRepo.deleteById(id);
            return true;
        }

        return false;
    }

    @Override
    public void updateCompanyRating(ReviewMessage reviewMessage) {
//ToDo
    }
}
