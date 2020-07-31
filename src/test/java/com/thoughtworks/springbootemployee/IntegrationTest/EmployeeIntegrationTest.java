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
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        Employee employee = new Employee(null, "alibaba1", 20, "male", 6000);
        Company company = new Company(null, "alibaba", 1, Collections.emptyList());
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
        Employee employee = new Employee(null, "alibaba1", 20, "male", 6000);
        Company company = new Company(null, "alibaba", 1, Collections.emptyList());
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

    @Test
    void should_insert_employee_when_hit_post_employee_given_employee() throws Exception {
        //given
        String employeeJson = "{\n" +
                "    \"id\": 1, \n" +
                "    \"name\": \"Xiaoxia\", \n" +
                "    \"age\": 15, \n" +
                "    \"gender\": \"Female\"\n" +
                "}";

        mockMvc.perform(post("/employees/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeJson))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Xiaoxia"))
                .andExpect(jsonPath("$.age").value(15))
                .andExpect(jsonPath("$.gender").value("Female"));
    }

    @Test
    void should_return_employee_list_when_hit_get_EmployeesPagination_given_page_pageSize() throws Exception {
        //given
        Employee firstEmployee = new Employee(null, "alibaba3", 20, "male", 6000);
        Employee secondEmployee = new Employee(null, "alibaba4", 21, "male", 6000);
        employeeRepository.save(firstEmployee);
        employeeRepository.save(secondEmployee);

        List<Employee> employees = employeeRepository.findAll(PageRequest.of(1, 3)).getContent();

        mockMvc.perform(get("/employees?page=1&pageSize=2"))
                .andExpect(jsonPath("$.content.size()").value(2))
                .andExpect(jsonPath("$.content[0].name").value(firstEmployee.getName()))
                .andExpect(jsonPath("$.content[1].name").value(secondEmployee.getName()));
    }
}
