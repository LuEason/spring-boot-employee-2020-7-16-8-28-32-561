package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.requestModel.RequestEmployee;
import org.springframework.beans.BeanUtils;

public class EmployeeMapper {

    public Employee requestEmployeeToEmployee(RequestEmployee requestEmployee) {
        if (requestEmployee != null) {
            Employee employee = new Employee();
            BeanUtils.copyProperties(requestEmployee, employee);
            return employee;
        }
        return null;
    }
}
