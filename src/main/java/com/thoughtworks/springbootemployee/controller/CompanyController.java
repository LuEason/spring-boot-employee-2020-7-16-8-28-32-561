package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping(params = {"page", "pageSize"})
    public Page<Company> getCompaniesByPageAndPageSize(int page, int pageSize) {
        return companyService.findAll(page, pageSize);
    }

    @GetMapping
    public List<Company> getAllCompanies() {
        return companyService.findAll();
    }

    @GetMapping("/{id}")
    public Company getCompanyById(@PathVariable int id) {
        return companyService.findById(id);
    }

    @GetMapping("/{id}/employees")
    public List<Employee> getEmployeesByCompanyId(@PathVariable int id) {
        return companyService.findEmployeesById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company addCompany(@RequestBody Company company) {
        return companyService.save(company);
    }

    @PutMapping("/{id}")
    public Company updateCompany(@PathVariable int id, @RequestBody Company newCompany) {
        return companyService.updateCompany(id, newCompany);
    }

    @DeleteMapping("/{id}")
    public boolean deleteAllEmployeesByCompanyId(@PathVariable int id) {
        return companyService.deleteById(id);
    }
}
