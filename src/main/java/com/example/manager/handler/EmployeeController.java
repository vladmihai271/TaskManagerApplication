package com.example.manager.handler;

import com.example.manager.core.domain.Employee;
import com.example.manager.core.application.employee.EmployeeInterface;
import com.example.manager.core.domain.EmployeeSimplified;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @PostMapping("/employees/{userId}")
    public Optional<Employee> postEmployee(@RequestBody EmployeeSimplified employee, @PathVariable Long userId)
    {

        Optional<Employee> userEmployee = employeeInterface.getEmployeeById(userId);
        if(userEmployee.isPresent() && userEmployee.get().getSecurityAccess().equals("HR")) {
            return Optional.ofNullable(employeeInterface.saveEmployee(employee));
        }
        return Optional.empty();
    }
    @Operation(summary = "Retrieves all employees from the database",
            description = "Retrieves all employees from the database as a list. " +
                    "If there are no employees in the database returns empty list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully received"),
            @ApiResponse(responseCode = "404", description = "Endpoint not exposed")
    })
    @RequestMapping(value = "/employees/{userId}", method = RequestMethod.GET)
    public List<Employee> getAllEmployees(@PathVariable Long userId)
    {
        Optional<Employee> userEmployee = employeeInterface.getEmployeeById(userId);
        if(userEmployee.isPresent()) {
            return employeeInterface.getEmployeeList();
        }
        return null;
    }
    @Operation(summary = "Retrieves one employee from the database using its id",
            description = "Gets one employee from the database using id (the primary key). " +
                    "If no employee is found with that id, Optional.empty() is returned")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully received"),
            @ApiResponse(responseCode = "404", description = "Endpoint not exposed")
    })
    @RequestMapping(value = "/employees/{employeeId}/{userId}", method = RequestMethod.GET)
    public Optional<Employee> getEmployeeById(@PathVariable Long employeeId, @PathVariable Long userId)
    {
        Optional<Employee> userEmployee = employeeInterface.getEmployeeById(userId);
        if(userEmployee.isPresent()) {
            return employeeInterface.getEmployeeById(employeeId);
        }
        return Optional.empty();
    }
    @Operation(summary = "Updates the employee with the given id",
            description = "Updates the employee with the given id with the" +
                    " employee object sent through the body of the request. " +
                    "If no employee with that id is found returns Optional.empty().")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully received"),
            @ApiResponse(responseCode = "404", description = "Endpoint not exposed")
    })
    @RequestMapping(value = "/employees/{employeeId}/{userId}", method = RequestMethod.PUT)
    public Optional<Employee> updateEmployeeById(@PathVariable Long employeeId,
                                                 @RequestBody EmployeeSimplified employee,
                                                 @PathVariable Long userId)
    {
        Optional<Employee> userEmployee = employeeInterface.getEmployeeById(userId);
        if(userEmployee.isPresent() && userEmployee.get().getSecurityAccess().equals("HR")) {
            return employeeInterface.updateEmployeeById(employee, employeeId);
        }
        return Optional.empty();
    }
    @Operation(summary = "Retrieves one employee from the database using its username",
            description = "Gets one employee from the database using username. " +
                    "If no employee is found with that username, Optional.empty() is returned")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully received"),
            @ApiResponse(responseCode = "404", description = "Endpoint not exposed")
    })
    @RequestMapping(value = "/employees/{employeeUsername}/{userId}", method = RequestMethod.GET)
    public Optional<Employee> getEmployeeByUsername(@PathVariable String employeeUsername,
                                                    @PathVariable Long userId)
    {
        Optional<Employee> userEmployee = employeeInterface.getEmployeeById(userId);
        if(userEmployee.isPresent()) {
            return employeeInterface.findEmployeeByUsername(employeeUsername);
        }
        return Optional.empty();
    }
    @Operation(summary = "Deletes one employee by id",
            description = "Deletes one employee by id. " +
                    "If employee with id is not found nothing happens, returned status is 200-OK")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully received"),
            @ApiResponse(responseCode = "404", description = "Endpoint not exposed")
    })
    @RequestMapping(value = "/employees/{employeeId}/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable Long employeeId, @PathVariable Long userId)
    {
        Optional<Employee> employee = employeeInterface.getEmployeeById(userId);
        if(employee.isPresent() && employee.get().getSecurityAccess().equals("HR")) {
            employeeInterface.deleteEmployeeById(employeeId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
