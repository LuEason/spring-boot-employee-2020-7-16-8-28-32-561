package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Page<Company> findAll(int page, int pageSize) {
        return companyRepository.findAll(PageRequest.of(page, pageSize));
    }

    public Company findById(int id) {
        return companyRepository.findById(id).orElse(null);
    }

    public Company save(Company newCompany) {
        return companyRepository.save(newCompany);
    }

    public Company updateCompany(int id, Company updatedCompany) {
        Company targetCompany = findById(id);
        if (targetCompany != null) {
            if (updatedCompany.getCompanyName() != null)
                targetCompany.setCompanyName(updatedCompany.getCompanyName());
            if (updatedCompany.getEmployeeNumber() != null)
                targetCompany.setEmployeeNumber(updatedCompany.getEmployeeNumber());
            if (updatedCompany.getEmployees() != null)
                targetCompany.setEmployees(updatedCompany.getEmployees());
            save(targetCompany);
        }
        return targetCompany;
    }

    public boolean deleteById(int id) {
        companyRepository.deleteById(id);
        return !companyRepository.findById(id).isPresent();
    }
}
