package com.example.manager.core.application.employee;
import com.example.manager.core.domain.Employee;
import com.example.manager.core.domain.EmployeeSimplified;

import java.util.List;
import java.util.Optional;

public interface EmployeeInterface {
    // save operation
    Employee saveEmployee(EmployeeSimplified employee);

    // read operation
    List<Employee> getEmployeeList();
    Optional<Employee> getEmployeeById(Long employeeId);

    public Optional<Employee> findEmployeeByUsername(String username);

    // update operation
    Optional<Employee> updateEmployeeById(EmployeeSimplified employee, Long employeeId);

    // delete operation
    void deleteEmployeeById(Long employeeId);
}