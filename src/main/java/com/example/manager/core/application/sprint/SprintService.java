package com.example.manager.core.application.sprint;

import com.example.manager.core.application.repositories.SprintRepository;
import com.example.manager.core.application.repositories.TaskRepository;
import com.example.manager.core.domain.*;
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
    public Sprint completeMissingFieldsFromUpdateSprintObject(Long id, SprintSimplified newSprintReceivedAtUpdate){
        Optional<Sprint> sprintToBeChanged = sprintRepository.findById(id);
        if(sprintToBeChanged.isEmpty()){
            return null;
        }

        if(newSprintReceivedAtUpdate.getTeam() == null || newSprintReceivedAtUpdate.getTeam().isEmpty() ||
                newSprintReceivedAtUpdate.getTeam().isBlank())
        {
            newSprintReceivedAtUpdate.setTeam(sprintToBeChanged.get().getTeam());
        }
        if(newSprintReceivedAtUpdate.getStatus() == null || newSprintReceivedAtUpdate.getStatus().isEmpty() ||
                newSprintReceivedAtUpdate.getStatus().isBlank())
        {
            newSprintReceivedAtUpdate.setStatus(sprintToBeChanged.get().getStatus());
        }
        if(newSprintReceivedAtUpdate.getProject() == null || newSprintReceivedAtUpdate.getProject().isEmpty() ||
                newSprintReceivedAtUpdate.getProject().isBlank())
        {
            newSprintReceivedAtUpdate.setProject(sprintToBeChanged.get().getProject());
        }
        if(newSprintReceivedAtUpdate.getTitle() == null || newSprintReceivedAtUpdate.getTitle().isEmpty() ||
                newSprintReceivedAtUpdate.getTitle().isBlank())
        {
            newSprintReceivedAtUpdate.setTitle(sprintToBeChanged.get().getTitle());
        }
        if(newSprintReceivedAtUpdate.getUid() == null)
        {
            newSprintReceivedAtUpdate.setUid(sprintToBeChanged.get().getUid());
        }

        sprintToBeChanged.get().setStatus(newSprintReceivedAtUpdate.getStatus());
        sprintToBeChanged.get().setTeam(newSprintReceivedAtUpdate.getTeam());
        sprintToBeChanged.get().setProject(newSprintReceivedAtUpdate.getProject());
        sprintToBeChanged.get().setTitle(newSprintReceivedAtUpdate.getTitle());
        sprintToBeChanged.get().setUid(newSprintReceivedAtUpdate.getUid());

        return sprintToBeChanged.get();
    }
}
