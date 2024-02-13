package com.example.manager.handler;

import com.example.manager.core.application.employee.EmployeeInterface;
import com.example.manager.core.application.sprint.SprintInterface;
import com.example.manager.core.domain.Employee;
import com.example.manager.core.domain.Project;
import com.example.manager.core.domain.Sprint;
import com.example.manager.core.domain.SprintSimplified;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SprintControllerTest {
    private SprintController sprintController;
    @Mock
    private SprintInterface sprintInterface;
    @Mock
    private EmployeeInterface employeeInterface;
    @BeforeEach
    public void beforeEach() {
        sprintController = new SprintController(sprintInterface, employeeInterface);
    }
/*    @Test
    public void postSprintTestSuccess() {
        Optional<Employee> userEmployeeOptional = Optional.of(new Employee(1L,"team","tasks","projects",
                "availability","name","surname","username","password",
                "Department Chief"));
        SprintSimplified sprintSimplified = new SprintSimplified(1L,"team","status","project","title");
        Sprint sprint = new Sprint(1L,"team","tasks","status","project","title");
        Mockito.when(sprintInterface.saveSprintSimplified(sprintSimplified)).thenReturn(Optional.of(sprint));
        Mockito.when(employeeInterface.getEmployeeById(1L)).thenReturn(userEmployeeOptional);
        assertDoesNotThrow(() -> sprintController.postSprint(sprintSimplified,1L));
    }*/
}
