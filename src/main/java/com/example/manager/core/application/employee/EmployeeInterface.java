package com.example.manager.core.application.employee;
import com.example.manager.core.domain.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeInterface {
    // save operation
    Employee saveEmployee(Employee employee);

    // read operation
    List<Employee> getEmployeeList();
    Optional<Employee> getEmployeeById(Long employeeId);

    public Employee findEmployeeByUsername(String username);

    // update operation
    Optional<Employee> updateEmployeeById(Employee employee, Long employeeId);

    // delete operation
    void deleteEmployeeById(Long employeeId);
}