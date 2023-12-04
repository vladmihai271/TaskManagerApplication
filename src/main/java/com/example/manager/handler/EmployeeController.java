package com.example.manager.handler;

import com.example.manager.core.domain.Employee;
import com.example.manager.core.application.employee.EmployeeInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class EmployeeController {

    private final EmployeeInterface employeeInterface;

    public EmployeeController(EmployeeInterface employeeInterface) {
        this.employeeInterface = employeeInterface;
    }
    @PostMapping("/employees")
    public void postEmployee(@RequestBody Employee employee)
    {
        employeeInterface.saveEmployee(employee);
    }
    @RequestMapping(value = "/employees", method = RequestMethod.GET)
    public List<Employee> getAllEmployees()
    {
        return employeeInterface.getEmployeeList();
    }
    @RequestMapping(value = "/employees/{employeeId}", method = RequestMethod.GET)
    public Optional<Employee> getEmployeeById(@PathVariable Long employeeId)
    {
        return employeeInterface.getEmployeeById(employeeId);
    }
    @RequestMapping(value = "/employees/{employeeId}", method = RequestMethod.PUT)
    public Optional<Employee> updateEmployeeById(@PathVariable Long employeeId, @RequestBody Employee employee)
    {
        return employeeInterface.updateEmployeeById(employee, employeeId);
    }
    @RequestMapping(value = "/employees/{employeeUsername}", method = RequestMethod.GET)
    public Employee updateEmployeeById(@PathVariable String employeeUsername)
    {
        return employeeInterface.findEmployeeByUsername(employeeUsername);
    }
}
