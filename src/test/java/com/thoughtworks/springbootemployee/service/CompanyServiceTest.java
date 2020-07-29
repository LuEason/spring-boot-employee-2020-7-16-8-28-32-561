package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CompanyServiceTest {
    private CompanyRepository mockedCompanyRepository;
    private CompanyService companyService;

    private List<Company> generateCompanies() {
        List<Company> companies = new ArrayList<>();
        List<Employee> firstEmployees = new ArrayList<>();
        firstEmployees.add(new Employee(0, "alibaba1", 20, "male", 6000));
        firstEmployees.add(new Employee(1, "alibaba2", 19, "female", 7000));
        firstEmployees.add(new Employee(2, "alibaba3", 19, "male", 8000));
        companies.add(new Company(0, "alibaba", 3, firstEmployees));
        List<Employee> secondEmployees = new ArrayList<>();
        secondEmployees.add(new Employee(3, "baidu1", 20, "male", 6000));
        secondEmployees.add(new Employee(4, "baidu2", 19, "female", 7000));
        secondEmployees.add(new Employee(5, "baidu3", 19, "male", 8000));
        companies.add(new Company(1, "baidu", 3, secondEmployees));
        List<Employee> thirdEmployees = new ArrayList<>();
        thirdEmployees.add(new Employee(6, "tencent1", 20, "male", 6000));
        thirdEmployees.add(new Employee(7, "tencent2", 19, "female", 7000));
        thirdEmployees.add(new Employee(8, "tencent3", 19, "male", 8000));
        companies.add(new Company(2, "tencent", 3, thirdEmployees));
        return companies;
    }

    @BeforeEach
    void init() {
        mockedCompanyRepository = Mockito.mock(CompanyRepository.class);
        companyService = new CompanyService(mockedCompanyRepository);
    }

    @Test
    void should_return_all_companies_when_get_all_company_given_no_parameter() {
        //given
        when(mockedCompanyRepository.findAll()).thenReturn(generateCompanies());

        //when
        List<Company> companies = companyService.findAll();

        //then
        assertEquals(3, companies.size());
    }

    @Test
    void should_return_tencent_when_get_company_by_id_given_2() {
        //given
        int id = 2;
        when(mockedCompanyRepository.findById(id)).thenReturn(generateCompanies().stream().filter(employee -> employee.getId() == id).findFirst());

        //when
        Company company = companyService.findById(id);

        //then
        assertEquals("tencent", company.getCompanyName());
    }

    @Test
    void should_return_tencent_employees_when_get_company_by_id_given_2() {
        //given
        int id = 2;
        when(mockedCompanyRepository.findById(id)).thenReturn(generateCompanies().stream().filter(employee -> employee.getId() == id).findFirst());

        //when
        Company company = companyService.findById(id);

        //then
        assertEquals("tencent1", company.getEmployees().get(0).getName());
    }

    @Test
    void should_return_companies_in_the_specified_range_when_get_employees_given_page_2_and_page_size_2() {
        //given
        int page = 2;
        int pageSize = 2;
        //when
        companyService.findAll(page, pageSize);

        //then
        Mockito.verify(mockedCompanyRepository).findAll(PageRequest.of(page, pageSize));
    }
}
