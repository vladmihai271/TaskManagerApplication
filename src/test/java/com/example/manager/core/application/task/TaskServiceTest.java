package com.example.manager.core.application.task;

import com.example.manager.core.application.employee.EmployeeService;
import com.example.manager.core.application.repositories.EmployeeRepository;
import com.example.manager.core.application.repositories.SprintRepository;
import com.example.manager.core.application.repositories.TaskRepository;
import com.example.manager.core.application.sprint.SprintService;
import com.example.manager.core.domain.Employee;
import com.example.manager.core.domain.Sprint;
import com.example.manager.core.domain.Task;
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    private TaskService taskService;
    @Mock
    @Getter
    private TaskRepository taskRepository;
    @Mock
    @Getter
    private EmployeeRepository employeeRepository;
    @Mock
    @Getter
    private EmployeeService employeeService;
    @Mock
    @Getter
    private SprintRepository sprintRepository;

    @BeforeEach
    public void beforeEach(){
        taskService = new TaskService(employeeRepository, taskRepository, employeeService, sprintRepository);
    }
    @Test
    public void addTaskToEmployeeTestSuccess() {
        Task task = new Task(1L,"task1","description","sprint","status","project","asignee",3);
        Mockito.when(employeeRepository.findByUsername("asignee")).thenReturn(new Employee(1L,"team","task1,","projects",
                "availability","name","surname","username","password", "securityAccess"));
        assertDoesNotThrow(() -> taskService.addTaskToEmployee(task));
    }
    @Test
    public void addTaskToEmployeeTestSuccessSecondCase() {
        Task task = new Task(1L,"task1","description","sprint","status","project","asignee",3);
        Mockito.when(employeeRepository.findByUsername("asignee")).thenReturn(new Employee(1L,"team","","projects",
                "availability","name","surname","username","password", "securityAccess"));
        assertDoesNotThrow(() -> taskService.addTaskToEmployee(task));
    }
    @Test
    public void addTaskToSprintSuccess() {
        Task task = new Task(1L,"task1","description","sprint",
                "status","project","asignee",3);
        Mockito.when(sprintRepository.findByTitle("sprint")).thenReturn(new Sprint(1L,"team","task1,",
                "status", "project","title"));
        assertDoesNotThrow(() -> taskService.addTaskToSprint(task));
    }
    @Test
    public void addTaskToSprintSuccessSecondCase() {
        Task task = new Task(1L,"task1","description","sprint",
                "status","project","asignee",3);
        Mockito.when(sprintRepository.findByTitle("sprint")).thenReturn(new Sprint(1L,"team","",
                "status", "project","title"));
        assertDoesNotThrow(() -> taskService.addTaskToSprint(task));
    }
    @Test
    public void findEmployeeByTaskIdTestSuccess() {
        Task task = new Task(1L,"task1","description","sprint",
                "status","project","asignee",3);
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        Mockito.when(employeeRepository.findAll()).thenReturn(List.of(new Employee(1L,"team","task1,","projects",
                "availability","name","surname","username",
                "password", "securityAccess")));
        assertDoesNotThrow(() -> taskService.findEmployeeByTaskId(1L));
        assertThat(taskService.findEmployeeByTaskId(1L)).isEqualTo(Optional.of(1L));
    }
    @Test
    public void findEmployeeByTaskIdTestFailure() {
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.empty());
        assertDoesNotThrow(() -> taskService.findEmployeeByTaskId(1L));
        assertThat(taskService.findEmployeeByTaskId(1L)).isEqualTo(Optional.empty());
    }
    @Test
    public void deleteTaskFromEmployeeTestSuccess() {
        Task task = new Task(1L,"task1","description","sprint",
                "status","project","asignee",3);
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(new Employee(1L,"team","task1,","projects",
                "availability","name","surname","username","password", "securityAccess")));
        Mockito.when(employeeRepository.findAll()).thenReturn(List.of(new Employee(1L,"team","task1,","projects",
                "availability","name","surname","username",
                "password", "securityAccess")));
        assertDoesNotThrow(() -> taskService.deleteTaskFromEmployee(1L));
    }
    @Test
    public void completeMissingFieldsFromUpdateTaskObjectTestSuccess() {
        Task task = new Task(1L,"task1","description","sprint",
                "status","project","asignee",3);
        Task task2 = new Task(1L,"","","",
                "","","",3);
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        assertThat(taskService.completeMissingFieldsFromUpdateTaskObject(1L, task2)).isEqualTo(task);
    }
}
