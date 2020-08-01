package com.thoughtworks.springbootemployee.IntegrationTest;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyIntegrationTest {
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
    void should_return_companies_when_hit_get_all_companies_given_nothing() throws Exception {
        Company firstCompany = new Company(null, "alibaba", 0, Collections.emptyList());
        Company secondCompany = new Company(null, "baidu", 0, Collections.emptyList());
        List<Company> companies = companyRepository.saveAll(asList(firstCompany, secondCompany));

        mockMvc.perform(get("/companies"))
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].companyName").value(firstCompany.getCompanyName()))
                .andExpect(jsonPath("$[1].companyName").value(secondCompany.getCompanyName()));
    }
}
