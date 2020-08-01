package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.requestModel.RequestCompany;
import org.springframework.beans.BeanUtils;

public class CompanyMapper {

    public Company requestCompanyToCompany(RequestCompany requestCompany) {
        if (requestCompany != null) {
            Company company = new Company();
            BeanUtils.copyProperties(requestCompany, company);
            return company;
        }
        return null;
    }
}
