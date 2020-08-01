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
class CompanyIntegrationTest {
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

    @Test
    void should_return_company_when_hit_get_company_by_id_given_id() throws Exception {
        Company company = new Company(null, "alibaba", 0, Collections.emptyList());
        company = companyRepository.save(company);

        mockMvc.perform(get("/companies/" + company.getId()))
                .andExpect(jsonPath("$.companyName").value(company.getCompanyName()))
                .andExpect(jsonPath("$.id").value(company.getId()))
                .andExpect(jsonPath("$.employeeNumber").value(company.getEmployeeNumber()));
    }

    @Test
    void should_return_page_of_companies_when_hit_get_companies_pagination_given_page_and_pageSize() throws Exception {
        int page = 1;
        int pageSize = 2;
        Company firstCompany = new Company(null, "alibaba", 0, Collections.emptyList());
        Company secondCompany = new Company(null, "baidu", 0, Collections.emptyList());
        List<Company> companies = companyRepository.saveAll(asList(firstCompany, secondCompany));

        mockMvc.perform(get(String.format("/companies?page=%s&pageSize=%s", page, pageSize)))
                .andExpect(jsonPath("$.content.size()").value(2))
                .andExpect(jsonPath("$.content[0].companyName").value(companies.get(0).getCompanyName()))
                .andExpect(jsonPath("$.content[1].companyName").value(companies.get(1).getCompanyName()));
    }
}
