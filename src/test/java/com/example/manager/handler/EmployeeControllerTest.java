package com.example.manager.handler;

import com.example.manager.core.application.employee.EmployeeInterface;
import com.example.manager.core.domain.Employee;
import com.example.manager.core.domain.EmployeeSimplified;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {
    private EmployeeController employeeController;
    @Mock
    EmployeeInterface employeeInterface;
    @BeforeEach
    public void beforeEach() {
        employeeController = new EmployeeController(employeeInterface);
    }
    @Test
    public void postEmployeeTestSuccess() {
        Optional<Employee> userEmployeeOptional = Optional.of(new Employee(1L,"team","tasks","projects",
                "availability","name","surname","username","password",
                "HR"));
        Optional<Employee> savedEmployeeOptional = Optional.of(new Employee(2L,"team2","tasks2","projects2",
                "availability2","name2","surname2","username2","password2",
                "securityAccess2"));
        EmployeeSimplified employeeSimplified = new EmployeeSimplified("team2","availability2",
                "name2","surname2","username2","password2","securityAccess2");
        Mockito.when(employeeInterface.getEmployeeById(1L)).thenReturn(userEmployeeOptional);
        Mockito.when(employeeInterface.saveEmployee(employeeSimplified)).thenReturn(savedEmployeeOptional.get());
        assertThat(employeeController.postEmployee(employeeSimplified,1L)).isEqualTo(savedEmployeeOptional);
    }
    @Test
    public void getAllEmployeesTestSuccess() {
        Optional<Employee> userEmployeeOptional = Optional.of(new Employee(1L,"team","tasks","projects",
                "availability","name","surname","username","password",
                "HR"));
        Mockito.when(employeeInterface.getEmployeeById(1L)).thenReturn(userEmployeeOptional);
        assertThat(employeeController.getAllEmployees(1L)).isEqualTo(employeeInterface.getEmployeeList());
    }
    @Test
    public void getEmployeeByIdTestSuccess() {
        Optional<Employee> userEmployeeOptional = Optional.of(new Employee(1L,"team","tasks","projects",
                "availability","name","surname","username","password",
                "HR"));
        Optional<Employee> returnEmployeeOptional = Optional.of(new Employee(2L,"team2","tasks2","projects2",
                "availability2","name2","surname2","username2","password2",
                "HR2"));
        Mockito.when(employeeInterface.getEmployeeById(1L)).thenReturn(userEmployeeOptional);
        Mockito.when(employeeInterface.getEmployeeById(2L)).thenReturn(returnEmployeeOptional);
        assertThat(employeeController.getEmployeeById(2L,1L)).isEqualTo(returnEmployeeOptional);
    }
    @Test
    public void updateEmployeeByIdTestSuccess() {
        Optional<Employee> userEmployeeOptional = Optional.of(new Employee(1L,"team","tasks","projects",
                "availability","name","surname","username","password",
                "HR"));
        Optional<Employee> returnEmployeeOptional = Optional.of(new Employee(2L,"team2","tasks2","projects2",
                "availability2","name2","surname2","username2","password2",
                "HR2"));
        EmployeeSimplified employeeSimplified = new EmployeeSimplified("team2","availability2",
                "name2","surname2","username2","password2","securityAccess2");
        Mockito.when(employeeInterface.getEmployeeById(1L)).thenReturn(userEmployeeOptional);
        Mockito.when(employeeInterface.updateEmployeeById(employeeSimplified,2L)).thenReturn(returnEmployeeOptional);
        assertThat(employeeController.updateEmployeeById(2L, employeeSimplified,1L)).isEqualTo(returnEmployeeOptional);
    }
    @Test
    public void deleteEmployeeByIdTestSuccess() {
        Optional<Employee> userEmployeeOptional = Optional.of(new Employee(1L,"team","tasks","projects",
                "availability","name","surname","username","password",
                "HR"));
        Mockito.when(employeeInterface.getEmployeeById(1L)).thenReturn(userEmployeeOptional);
        employeeController.deleteEmployeeById(1L,1L);
        Mockito.verify(employeeInterface, Mockito.times(1)).deleteEmployeeById(1L);
    }
    @Test
    public void getEmployeeByUsernameTestSuccess() {
        Optional<Employee> userEmployeeOptional = Optional.of(new Employee(1L,"team","tasks","projects",
                "availability","name","surname","username","password",
                "HR"));
        Mockito.when(employeeInterface.getEmployeeById(1L)).thenReturn(userEmployeeOptional);
        Mockito.when(employeeInterface.findEmployeeByUsername("username")).thenReturn(userEmployeeOptional);
        assertThat(employeeController.getEmployeeByUsername("username",1L)).isEqualTo(userEmployeeOptional);
    }
}
