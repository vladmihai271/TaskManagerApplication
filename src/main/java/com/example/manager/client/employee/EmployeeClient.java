package com.example.manager.client.employee;

import com.example.manager.core.application.employee.EmployeeService;
import com.example.manager.core.application.repositories.EmployeeRepository;
import com.example.manager.core.application.employee.EmployeeInterface;
import com.example.manager.core.domain.Employee;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeClient implements EmployeeInterface {

    private final EmployeeRepository employeeRepository;
    private final EmployeeService employeeService;

    public EmployeeClient(EmployeeRepository employeeRepository, EmployeeService employeeService) {
        this.employeeRepository = employeeRepository;
        this.employeeService = employeeService;
    }


    @Override
    public Employee saveEmployee(Employee employee) {
        employee.setUid(System.currentTimeMillis());
        return employeeRepository.save(employee);
    }
    @Override
    public List<Employee> getEmployeeList() {
        return (List<Employee>) employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId);
    }

    @Override
    public Employee findEmployeeByUsername(String username) {
        return (Employee) employeeRepository.findByUsername(username);
    }
    @Override
    public Optional<Employee> updateEmployeeById(Employee employee, Long employeeId) {
        if(employeeService.deleteEmployeeById(employeeId)){
            return Optional.of(employeeRepository.save(employee));
        }
        else
        {
            return Optional.empty();
        }
    }
    @Override
    public void deleteEmployeeById(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }
}