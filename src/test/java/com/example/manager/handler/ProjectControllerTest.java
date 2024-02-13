package com.example.manager.handler;

import com.example.manager.core.application.employee.EmployeeInterface;
import com.example.manager.core.application.project.ProjectInterface;
import com.example.manager.core.domain.Employee;
import com.example.manager.core.domain.Project;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
public class ProjectControllerTest {
    private ProjectController projectController;
    @Mock
    ProjectInterface projectInterface;
    @Mock
    EmployeeInterface employeeInterface;
    @BeforeEach
    public void beforeEach() {
        projectController = new ProjectController(projectInterface, employeeInterface);
    }
    @Test
    public void postProjectTestSuccess() {
        Optional<Employee> userEmployeeOptional = Optional.of(new Employee(1L,"team","tasks","projects",
                "availability","name","surname","username","password",
                "Department Chief"));
        Project project = new Project(2L,"team","status","title","description",false);

        Mockito.when(employeeInterface.getEmployeeById(1L)).thenReturn(userEmployeeOptional);
        Mockito.when(projectInterface.saveProject(project)).thenReturn(project);
        assertDoesNotThrow(() -> projectController.postProject(project,1L));

    }
    @Test
    public void getAllProjectsTestSuccess() {
        Optional<Employee> userEmployeeOptional = Optional.of(new Employee(1L,"team","tasks","projects",
                "availability","name","surname","username","password",
                "Department Chief"));
        Project project = new Project(2L,"team","status","title","description",false);
        Project project2 = new Project(3L,"team2","status2","title2","description2",true);
        Project project3 = new Project(4L,"team3","status3","title3","description3",false);
        Mockito.when(employeeInterface.getEmployeeById(1L)).thenReturn(userEmployeeOptional);
        Mockito.when(projectInterface.getProjectList()).thenReturn(java.util.List.of(project,project2,project3));
        assertThat(projectController.getAllProjects(1L)).isEqualTo(java.util.List.of(project,project2,project3));
    }
    @Test
    public void getProjectByIdTestSuccess() {
        Optional<Employee> userEmployeeOptional = Optional.of(new Employee(1L,"team","tasks","projects",
                "availability","name","surname","username","password",
                "Department Chief"));
        Optional<Project> project = Optional.of(new Project(2L,"team","status","title","description",false));
        Mockito.when(employeeInterface.getEmployeeById(1L)).thenReturn(userEmployeeOptional);
        Mockito.when(projectInterface.getProjectById(2L)).thenReturn(project);
        assertThat(projectController.getProjectById(2L,1L)).isEqualTo(project);
    }
}
