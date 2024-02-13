package com.example.manager.handler;

import com.example.manager.core.application.repositories.EmployeeRepository;
import com.example.manager.core.application.repositories.ProjectRepository;
import com.example.manager.core.application.task.TaskInterface;
import com.example.manager.core.domain.Employee;
import com.example.manager.core.domain.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {
    private TaskController taskController;
    @Mock
    TaskInterface taskInterface;
    @Mock
    EmployeeRepository employeeRepository;
    @Mock
    ProjectRepository projectRepository;
    @BeforeEach
    public void beforeEach() {
        taskController = new TaskController(taskInterface, employeeRepository, projectRepository);
    }
    @Test
    public void getTaskByIdTestSuccess() {
        Optional<Employee> employee = Optional.of( new Employee(1L, "team", "tasks",
                "projects", "availability", "name", "surname",
                "username", "password", "securityAccess"));
        Optional<Task> task = Optional.of(new Task(1L, "title", "description", "sprint",
                "status", "project", "assignee", 1));
        Mockito.when(employeeRepository.findById(1L)).thenReturn(employee);
        Mockito.when(taskInterface.getTaskById(1L)).thenReturn(task);
        assertThat(taskController.getTaskById(1L,1L)).isEqualTo(task);
    }
    @Test
    public void getTasksByTitleTestSuccess() {
        Optional<Employee> employee = Optional.of( new Employee(1L, "team", "tasks",
                "projects", "availability", "name", "surname",
                "username", "password", "securityAccess"));
        Optional<Task> task = Optional.of(new Task(1L, "title", "description", "sprint",
                "status", "project", "assignee", 1));
        Mockito.when(employeeRepository.findById(1L)).thenReturn(employee);
        Mockito.when(taskInterface.getTaskByTitle("title")).thenReturn(task);
        assertThat(taskController.getTaskByTitle("title", 1L)).isEqualTo(task);
    }
}
