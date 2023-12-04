package com.example.manager.handler;

import com.example.manager.core.domain.Employee;
import com.example.manager.core.application.employee.EmployeeInterface;
import com.example.manager.core.domain.EmployeeSimplified;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EmployeeController {

    private final EmployeeInterface employeeInterface;

    public EmployeeController(EmployeeInterface employeeInterface) {
        this.employeeInterface = employeeInterface;
    }
    @Operation(summary = "Adds an employee to the database", description = "Adds an employee to the database. " +
            "Receives employee data through the body of the request in json format")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully received"),
            @ApiResponse(responseCode = "404", description = "Endpoint not exposed")
    })
    @PostMapping("/employees")
    public void postEmployee(@RequestBody EmployeeSimplified employee)
    {
        employeeInterface.saveEmployee(employee);
    }
    @Operation(summary = "Retrieves all employees from the database",
            description = "Retrieves all employees from the database as a list. " +
                    "If there are no employees in the database returns empty list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully received"),
            @ApiResponse(responseCode = "404", description = "Endpoint not exposed")
    })
    @RequestMapping(value = "/employees", method = RequestMethod.GET)
    public List<Employee> getAllEmployees()
    {
        return employeeInterface.getEmployeeList();
    }
    @Operation(summary = "Retrieves one employee from the database using its id",
            description = "Gets one employee from the database using id (the primary key). " +
                    "If no employee is found with that id, Optional.empty() is returned")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully received"),
            @ApiResponse(responseCode = "404", description = "Endpoint not exposed")
    })
    @RequestMapping(value = "/employees/{employeeId}", method = RequestMethod.GET)
    public Optional<Employee> getEmployeeById(@PathVariable Long employeeId)
    {
        return employeeInterface.getEmployeeById(employeeId);
    }
    @Operation(summary = "Updates the employee with the given id",
            description = "Updates the employee with the given id with the" +
                    " employee object sent through the body of the request. " +
                    "If no employee with that id is found returns Optional.empty().")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully received"),
            @ApiResponse(responseCode = "404", description = "Endpoint not exposed")
    })
    @RequestMapping(value = "/employees/{employeeId}", method = RequestMethod.PUT)
    public Optional<Employee> updateEmployeeById(@PathVariable Long employeeId, @RequestBody EmployeeSimplified employee)
    {
        return employeeInterface.updateEmployeeById(employee, employeeId);
    }
    @Operation(summary = "Retrieves one employee from the database using its username",
            description = "Gets one employee from the database using username. " +
                    "If no employee is found with that username, Optional.empty() is returned")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully received"),
            @ApiResponse(responseCode = "404", description = "Endpoint not exposed")
    })
    @RequestMapping(value = "/employees/{employeeUsername}", method = RequestMethod.GET)
    public Employee getEmployeeByUsername(@PathVariable String employeeUsername)
    {
        return employeeInterface.findEmployeeByUsername(employeeUsername);
    }
}
