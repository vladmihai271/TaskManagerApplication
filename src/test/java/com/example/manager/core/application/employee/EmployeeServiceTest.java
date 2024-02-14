package com.example.manager.core.application.employee;

import com.example.manager.core.application.repositories.EmployeeRepository;
import com.example.manager.core.application.repositories.ProjectRepository;
import com.example.manager.core.application.repositories.TaskRepository;
import com.example.manager.core.domain.Employee;
import com.example.manager.core.domain.EmployeeSimplified;
import com.example.manager.core.domain.Task;
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    private EmployeeService employeeService;
    @Mock
    @Getter
    private EmployeeRepository employeeRepository;
    @Mock
    @Getter
    private TaskRepository taskRepository;
    @Mock
    @Getter
    private ProjectRepository projectRepository;
    @BeforeEach
    public void beforeEach(){
        employeeService = new EmployeeService(employeeRepository, taskRepository, projectRepository);
    }
    @Test
    public void deleteEmployeeByIdTestSuccess() {
        Optional<Employee> employee = Optional.of(new Employee(1L, "team", "tasks,",
                "projects","availability", "name", "surname", "username", "password", "securityAccess"));
        Task task = new Task(2L, "title", "description", "sprint", "status", "project", "asignee",3);
        Mockito.when(employeeRepository.findById(1L)).thenReturn(employee);
        Mockito.when(taskRepository.findByTitle("tasks")).thenReturn(task);
        doNothing().when(employeeRepository).deleteById(1L);
        doNothing().when(taskRepository).deleteById(2L);
        assertThat(employeeService.deleteEmployeeById(1L, false)).isTrue();
    }
    @Test
    public void deleteEmployeeByIdTestFailure() {
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.empty());
        assertThat(employeeService.deleteEmployeeById(1L, false)).isFalse();
    }
    @Test
    public void fillUnusedFieldsTestSuccess() {
        EmployeeSimplified employeeSimplified = new EmployeeSimplified("team", "availability",
                "name", "surname", "username", "password", "securityAccess");
        Optional<Long> id = Optional.of(1L);
        assertThat(employeeService.fillUnusedFields(employeeSimplified, id)).isEqualTo(new Employee(1L, "team",
                "","", "availability", "name", "surname", "username", "password", "securityAccess"));
    }
    @Test
    public void fillUnusedFieldsTestFailure() {
        EmployeeSimplified employeeSimplified = new EmployeeSimplified("team", "availability",
                "name", "surname", "username", "password", "securityAccess");
        assertThat(employeeService.fillUnusedFields(employeeSimplified, Optional.empty())).isEqualTo(new Employee(0L, "team",
                "","", "availability", "name", "surname", "username", "password", "securityAccess"));
    }
    @Test
    public void saveEmployeeSimplifiedTestSuccess() {
        EmployeeSimplified employeeSimplified = new EmployeeSimplified("team", "availability",
                "name", "surname", "username", "password", "securityAccess");
        assertDoesNotThrow(() -> employeeService.saveEmployeeSimplified(employeeSimplified));
    }
    @Test
    public void saveEmployeeTestSuccess() {
        Employee employee = new Employee(1L, "team", "","", "availability", "name", "surname", "username", "password", "securityAccess");
        Mockito.when(employeeRepository.save(employee)).thenReturn(employee);
        assertThat(employeeService.saveEmployee(employee)).isEqualTo(employee);
    }
    @Test
    public void completeMissingFieldsFromUpdateEmployeeObjectTestSuccess() {
        EmployeeSimplified employeeSimplified = new EmployeeSimplified("", "",
                "", "", "", "", "");
        Employee employee = new Employee(1L, "team", "","", "availability", "name", "surname", "username", "password", "securityAccess");
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        assertThat(employeeService.completeMissingFieldsFromUpdateEmployeeObject(1L, employeeSimplified)).isEqualTo(employee);
    }
}
