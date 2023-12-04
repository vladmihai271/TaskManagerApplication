package com.example.manager.core.application.employee;

import com.example.manager.core.application.repositories.EmployeeRepository;
import com.example.manager.core.domain.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public boolean deleteEmployeeById(Long employeeId){
        Optional<Employee> employeeToBeDeleted = employeeRepository.findById(employeeId);
        if(employeeToBeDeleted.isPresent())
        {
            employeeRepository.deleteById(employeeId);
            return true;
        }
        else
        {
            return false;
        }
    }
}
