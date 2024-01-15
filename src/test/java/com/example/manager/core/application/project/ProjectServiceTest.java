package com.example.manager.core.application.project;

import com.example.manager.core.application.repositories.SprintRepository;
import com.example.manager.core.application.repositories.TaskRepository;
import lombok.Getter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {
    private ProjectService projectService;
    @Mock
    @Getter
    private SprintRepository sprintRepository;
    @Mock
    @Getter
    private TaskRepository taskRepository;
}
