package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.NoSuchDataException;
import com.thoughtworks.springbootemployee.exception.NotTheSameIDException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Page<Employee> findAll(int page, int pageSize) {
        return employeeRepository.findAll(PageRequest.of(page - 1, pageSize));
    }

    public Employee findById(int id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public List<Employee> findAllByGender(String gender) {
        return employeeRepository.findAllByGender(gender);
    }

    public Employee save(Employee newEmployee) {
        return employeeRepository.save(newEmployee);
    }

    public Employee updateEmployee(int id, Employee updatedEmployee) throws NoSuchDataException, NotTheSameIDException {
        if (!updatedEmployee.getId().equals(id)) {
            throw new NotTheSameIDException();
        }
        Employee targetEmployee = findById(id);
        if (targetEmployee != null) {
            if (updatedEmployee.getName() != null)
                targetEmployee.setName(updatedEmployee.getName());
            if (updatedEmployee.getGender() != null)
                targetEmployee.setGender(updatedEmployee.getGender());
            if (updatedEmployee.getAge() != null)
                targetEmployee.setAge(updatedEmployee.getAge());
            if (updatedEmployee.getSalary() != null)
                targetEmployee.setSalary(updatedEmployee.getSalary());
            return save(targetEmployee);
        } else {
            throw new NoSuchDataException();
        }
    }

    public boolean deleteById(int id) throws NoSuchDataException {
        if (!employeeRepository.findById(id).isPresent()) {
            throw new NoSuchDataException();
        }
        employeeRepository.deleteById(id);
        return true;
    }
}
