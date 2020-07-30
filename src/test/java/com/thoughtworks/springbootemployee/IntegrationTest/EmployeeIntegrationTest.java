package com.thoughtworks.springbootemployee.IntegrationTest;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeIntegrationTest {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void afterEach() {
        companyRepository.deleteAll();
        employeeRepository.deleteAll();

    }

    @Test
    void should_return_employees_when_hit_get_employees_given_null() throws Exception {
        //given
        Employee employee = new Employee(3, "alibaba1", 20, "male", 6000);
        Company company = new Company(1, "alibaba", 1, Collections.emptyList());
        Company newCompany = companyRepository.save(company);
        employee.setCompanyId(newCompany.getId());
        employeeRepository.save(employee);

        mockMvc.perform(get("/employees"))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value(employee.getName()))
                .andExpect(jsonPath("$[0].age").value(employee.getAge()))
                .andExpect(jsonPath("$[0].gender").value(employee.getGender()))
                .andExpect(jsonPath("$[0].salary").value(employee.getSalary()))
                .andExpect(jsonPath("$[0].companyId").value(newCompany.getId()));
    }

    @Test
    void should_return_certain_employee_when_hit_get_employee_by_id_given_id() throws Exception {
        //given
        Employee employee = new Employee(3, "alibaba1", 20, "male", 6000);
        Company company = new Company(1, "alibaba", 1, Collections.emptyList());
        Company newCompany = companyRepository.save(company);
        employee.setCompanyId(newCompany.getId());
        Employee newEmployee = employeeRepository.save(employee);

        //when
        mockMvc.perform(get("/employees/" + newEmployee.getId()))
                .andExpect(jsonPath("$.id").value(newEmployee.getId()))
                .andExpect(jsonPath("$.name").value(newEmployee.getName()))
                .andExpect(jsonPath("$.age").value(newEmployee.getAge()))
                .andExpect(jsonPath("$.gender").value(newEmployee.getGender()))
                .andExpect(jsonPath("$.salary").value(newEmployee.getSalary()))
                .andExpect(jsonPath("$.companyId").value(newCompany.getId()));
    }
}
