package com.thoughtworks.springbootemployee.Eason;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @GetMapping
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(4, "alibaba1", 20, "male", 6000));
        employees.add(new Employee(11, "tengxun2", 19, "female", 7000));
        employees.add(new Employee(6, "alibaba3", 19, "male", 8000));
        return employees;
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable int id) {
        return new Employee(id, "alibaba3", 19, "male", 8000);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Employee> getEmployeesPagination(int page, int pageSize) {
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < pageSize; i++) {
            employees.add(new Employee(i, "alibaba3", 19, "male", 8000));
        }
        return employees;
    }

    @GetMapping(params = {"gender"})
    public List<Employee> getEmployeesByGender(String gender) {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(4, "alibaba1", 20, "male", 6000));
        employees.add(new Employee(11, "tengxun2", 19, "female", 7000));
        employees.add(new Employee(6, "alibaba3", 19, "male", 8000));
        return employees.stream().filter(employee -> employee.getGender().equals(gender)).collect(Collectors.toList());
    }

    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee) {
        return new Employee(employee.getId(), employee.getName(), employee.getAge(), employee.getGender(), employee.getSalary());
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable int id, @RequestBody Employee employee) {
        Employee newEmployee = new Employee();
        newEmployee.setId(employee.getId());
        newEmployee.setName(employee.getName());
        newEmployee.setGender(employee.getGender());
        newEmployee.setAge(employee.getAge());
        newEmployee.setSalary(employee.getSalary());
        return newEmployee;
    }

    @DeleteMapping("/{id}")
    public Employee deleteEmployee(@PathVariable int id) {
        return new Employee(id, "alibaba3", 19, "male", 8000);
    }
}
