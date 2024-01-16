package com.example.manager.core.application.sprint;

import com.example.manager.core.application.repositories.SprintRepository;
import com.example.manager.core.application.repositories.TaskRepository;
import com.example.manager.core.domain.Sprint;
import com.example.manager.core.domain.SprintSimplified;
import com.example.manager.core.domain.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SprintService {
    private final SprintRepository sprintRepository;
    private final TaskRepository taskRepository;


    public SprintService(SprintRepository sprintRepository, TaskRepository taskRepository) {
        this.sprintRepository = sprintRepository;
        this.taskRepository = taskRepository;
    }

    public Optional<Sprint> saveSprint(SprintSimplified sprintSimplified) {
        List<Sprint> sprintList = (List<Sprint>) sprintRepository.findAll();
        for (Sprint sprint : sprintList) {
            if (sprint.getTitle().equals(sprintSimplified.getTitle())
                    && sprint.getProject().equals(sprintSimplified.getProject())) {
                return Optional.empty();
            }
        }
        return Optional.of(sprintRepository.save(new Sprint(sprintSimplified)));
    }

    public List<Long> findTasksBySprintId(Long sprintId) {
        Optional<Sprint> sprint = sprintRepository.findById(sprintId);
        if(sprint.isPresent()) {
            String tasksString = sprint.get().getTasks();
            String[] tasksList = tasksString.split("\\s*,\\s*");
            List<Long> taskIds = new ArrayList<>();
            for (String s : tasksList) {
                taskIds.add(taskRepository.findByTitle(s).getUid());
            }
            return taskIds;
        }
        else {
            return null;
        }
    }
    public void deleteSprintFromTasks(Long sprintId) {
        List<Long> taskIds = findTasksBySprintId(sprintId);
        Optional<Sprint> sprint = sprintRepository.findById(sprintId);
        if(taskIds != null && sprint.isPresent()) {
            for (Long taskId : taskIds) {
                Optional<Task> task = taskRepository.findById(taskId);
                task.ifPresent(value -> value.setSprint(""));
            }
        }
    }

}
