package com.example.manager.client.sprint;

import com.example.manager.core.application.repositories.SprintRepository;
import com.example.manager.core.application.sprint.SprintInterface;
import com.example.manager.core.application.sprint.SprintService;
import com.example.manager.core.domain.Sprint;
import com.example.manager.core.domain.SprintSimplified;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class SprintClient implements SprintInterface {

    private final SprintRepository sprintRepository;
    private final SprintService sprintService;

    public SprintClient(SprintRepository sprintRepository, SprintService sprintService) {
        this.sprintRepository = sprintRepository;
        this.sprintService = sprintService;
    }

    @Override
    public Sprint saveSprintSimplified(SprintSimplified sprint) {
        return sprintService.saveSprint(sprint);

    }

    @Override
    public List<Sprint> getSprintList() {
        return (List<Sprint>) sprintRepository.findAll();
    }

    @Override
    public Optional<Sprint> getSprintById(Long sprintId) {
        return sprintRepository.findById(sprintId);
    }

    @Override
    public Optional<Sprint> findSprintByTitle(String title) {
        return Optional.ofNullable(sprintRepository.findByTitle(title));
    }

    @Override
    public Optional<Sprint> updateSprintById(SprintSimplified sprint, Long sprintId) { //change to be like in employee
        sprintRepository.deleteById(sprintId);
        saveSprintSimplified(sprint);
        return Optional.empty();
    }

    @Override
    public void deleteSprintById(Long sprintId) { //IMPLEMENTED METHODS IN SprintService
        sprintService.deleteSprintFromTasks(sprintId);
        sprintRepository.deleteById(sprintId);
    }
}
