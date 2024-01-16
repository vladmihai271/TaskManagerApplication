package com.example.manager.core.application.sprint;

import com.example.manager.core.application.repositories.SprintRepository;
import com.example.manager.core.application.repositories.TaskRepository;
import com.example.manager.core.domain.Sprint;
import com.example.manager.core.domain.SprintSimplified;
import com.example.manager.core.domain.Task;
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
public class SprintServiceTest {
    private SprintService sprintService;
    @Mock
    @Getter
    private SprintRepository sprintRepository;
    @Mock
    @Getter
    private TaskRepository taskRepository;
    @BeforeEach
    public void beforeEach(){
        sprintService = new SprintService(sprintRepository, taskRepository);
    }
    @Test
    public void saveSprintTestSuccess() {
        SprintSimplified sprintSimplified = new SprintSimplified(1L,"team","status","project", "title");
        Sprint sprint = new Sprint(sprintSimplified);
        Mockito.when(sprintRepository.save(sprint)).thenReturn(sprint);
        assertThat(sprintService.saveSprint(sprintSimplified)).isEqualTo(Optional.of(sprint));
    }
    @Test
    public void findTasksBySprintIdTestSuccess() {
        Sprint sprint = new Sprint(1L,"team","task1,",
                "status", "project","title");
        List<Long> taskIds = new ArrayList<>();
        taskIds.add(1L);
        Mockito.when(sprintRepository.findById(1L)).thenReturn(Optional.of(sprint));
        Mockito.when(taskRepository.findByTitle("task1")).thenReturn(new Task(1L,"task1",
                "description","sprint","status","project","asignee",3));
        assertThat(sprintService.findTasksBySprintId(1L)).isEqualTo(taskIds);
    }
    @Test
    public void findTasksBySprintIdTestFailure() {
        Mockito.when(sprintRepository.findById(1L)).thenReturn(Optional.empty());
        assertThat(sprintService.findTasksBySprintId(1L)).isNull();
    }
    @Test
    public void deleteSprintFromTasksTestSuccess() {
        Sprint sprint = new Sprint(1L,"team","task1,",
                "status", "project","title");
        List<Long> taskIds = new ArrayList<>();
        taskIds.add(1L);
        Mockito.when(sprintRepository.findById(1L)).thenReturn(Optional.of(sprint));
        Mockito.when(taskRepository.findByTitle("task1")).thenReturn(new Task(1L,"task1",
                "description","sprint","status","project","asignee",3));
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(new Task(1L,"task1",
                "description","sprint","status","project","asignee",3)));
        assertDoesNotThrow(() -> sprintService.deleteSprintFromTasks(1L));
    }
}
