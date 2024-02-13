package com.example.manager.core.application.project;

import com.example.manager.core.application.repositories.EmployeeRepository;
import com.example.manager.core.application.repositories.ProjectRepository;
import com.example.manager.core.application.repositories.SprintRepository;
import com.example.manager.core.application.repositories.TaskRepository;
import com.example.manager.core.application.task.TaskService;
import com.example.manager.core.domain.Employee;
import com.example.manager.core.domain.Project;
import lombok.Getter;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {
    private ProjectService projectService;
    @Mock
    @Getter
    private EmployeeRepository employeeRepository;
    @Mock
    @Getter
    private SprintRepository sprintRepository;
    @Mock
    @Getter
    private TaskRepository taskRepository;
    @Mock
    @Getter
    private TaskService taskService;
    @Mock
    @Getter
    private ProjectRepository projectRepository;
    @BeforeEach
    public void beforeEach(){
        projectService = new ProjectService(employeeRepository,sprintRepository, taskRepository, taskService, projectRepository);
    }
    @Test
    public void completeMissingFieldsTestSuccess() {
        Mockito.when(projectRepository.findById(1L)).
                thenReturn(java.util.Optional.of(new Project(1L,"team","status",
                        "title","description",false)));
        Project project = new Project();
        project.setUid(2L);
        assertThat(projectService.completeMissingFieldsFromUpdateProjectObject(1L, project)).
                isEqualTo(new Project(2L,"team","status",
                "title","description",false));

    }
    @Test
    public void completeMissingFieldsTestSuccess2() {
        Mockito.when(projectRepository.findById(1L)).
                thenReturn(java.util.Optional.of(new Project(1L,"team","status",
                        "title","description",false)));
        Project project = new Project(2L,"","","","",false);
        assertThat(projectService.completeMissingFieldsFromUpdateProjectObject(1L, project)).
                isEqualTo(new Project(2L,"team","status",
                        "title","description",false));

    }
    @Test
    public void deleteProjectFromMembersOfTeamTestSuccess() {
        Employee employee = new Employee(1L,"team","tasks","projects",
                "availability","name","surname","username","password",
                "HR");
        Mockito.when(employeeRepository.findAllByTeam("team")).thenReturn(java.util.List.of(employee));
        assertDoesNotThrow(() -> projectService.deleteProjectFromMembersOfTeam(new Project(1L,"team","status",
                "title","description",false)));
    }
}
