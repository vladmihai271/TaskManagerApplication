package com.example.manager.client.employee;

import com.example.manager.core.application.employee.EmployeeService;
import com.example.manager.core.application.repositories.EmployeeRepository;
import com.example.manager.core.application.employee.EmployeeInterface;
import com.example.manager.core.domain.Employee;
import com.example.manager.core.domain.EmployeeSimplified;
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
    public Employee saveEmployee(EmployeeSimplified employee) {
        return employeeService.saveEmployeeSimplified(employee);
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
    public Optional<Employee> findEmployeeByUsername(String username) {
        return Optional.ofNullable(employeeRepository.findByUsername(username));
    }
    @Override
    public Optional<Employee> updateEmployeeById(EmployeeSimplified employee, Long employeeId) {
        Optional<Employee> employeeBeforeUpdate = employeeRepository.findById(employeeId);
        if(employeeBeforeUpdate.isEmpty()){
            return Optional.empty();
        }
        boolean teamChanged = false;
        if(employee.getTeam()!=null && !employeeBeforeUpdate.get().getTeam().equals(employee.getTeam())){
            teamChanged = true;
        }
        Employee newEmployee = employeeService.completeMissingFieldsFromUpdateEmployeeObject(employeeId, employee);

        if(teamChanged){
            newEmployee.setProjects("");
            employeeService.addProjectsToNewEmployee(newEmployee);
        }
        if(employeeService.deleteEmployeeById(employeeId)){
            newEmployee.setUid(employeeId);
            return Optional.of(employeeRepository.save(newEmployee));
        }
        else
        {
            return Optional.empty();
        }
    }
    @Override
    public void deleteEmployeeById(Long employeeId) {
        employeeService.deleteEmployeeById(employeeId);
    }
}