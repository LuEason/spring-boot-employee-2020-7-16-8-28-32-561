package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.NoSuchDataException;
import com.thoughtworks.springbootemployee.exception.NotTheSameIDException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;

    public CompanyService(CompanyRepository companyRepository, EmployeeRepository employeeRepository) {
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Page<Company> findAll(int page, int pageSize) {
        return companyRepository.findAll(PageRequest.of(page - 1, pageSize));
    }

    public Company findById(int id) {
        return companyRepository.findById(id).orElse(null);
    }

    @Transient
    public Company save(Company newCompany) {
        Company returnCompany = companyRepository.save(newCompany);
        for (Employee employee : returnCompany.getEmployees()) {
            employee.setCompanyId(returnCompany.getId());
            employeeRepository.save(employee);
        }
        return returnCompany;
    }

    public Company updateCompany(int id, Company updatedCompany) throws NotTheSameIDException, NoSuchDataException {
        if (!updatedCompany.getId().equals(id)) {
            throw new NotTheSameIDException();
        }
        Company targetCompany = findById(id);
        if (targetCompany != null) {
            if (updatedCompany.getCompanyName() != null)
                targetCompany.setCompanyName(updatedCompany.getCompanyName());
            if (updatedCompany.getEmployeeNumber() != null)
                targetCompany.setEmployeeNumber(updatedCompany.getEmployeeNumber());
            if (updatedCompany.getEmployees() != null)
                targetCompany.setEmployees(updatedCompany.getEmployees());
            return save(targetCompany);
        } else {
            throw new NoSuchDataException();
        }
    }

    public List<Employee> findEmployeesById(int id) {
        if (companyRepository.findById(id).isPresent()) {
            return companyRepository.findById(id).get().getEmployees();
        }
        return new ArrayList<>();
    }

    public boolean deleteById(int id) throws NoSuchDataException {
        if (!companyRepository.findById(id).isPresent()) {
            throw new NoSuchDataException();
        }
        // NoSuchDataException
        companyRepository.deleteById(id);
        return true;
    }
}
