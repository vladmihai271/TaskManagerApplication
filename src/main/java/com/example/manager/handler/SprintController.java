package com.example.manager.handler;

import com.example.manager.core.application.sprint.SprintInterface;
import com.example.manager.core.domain.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class SprintController {
    private final SprintInterface sprintInterface;

    public SprintController(SprintInterface sprintInterface) {
        this.sprintInterface = sprintInterface;
    }
    @PostMapping("/sprints")
    public void postSprint(@RequestBody SprintSimplified sprint)
    {
        sprintInterface.saveSprintSimplified(sprint);
    }
    @RequestMapping(value = "/sprints", method = RequestMethod.GET)
    public List<Sprint> getAllSprints()
    {
        return sprintInterface.getSprintList();
    }
    @RequestMapping(value = "/sprints/{sprintId}", method = RequestMethod.GET)
    public Optional<Sprint> getSprintById(@PathVariable Long sprintId)
    {
        return sprintInterface.getSprintById(sprintId);
    }
    @RequestMapping(value = "/sprints/{sprintId}", method = RequestMethod.PUT)
    public Optional<Sprint> updateSprintById(@PathVariable Long sprintId, @RequestBody SprintSimplified sprintSimplified)
    {
        return sprintInterface.updateSprintById(sprintSimplified, sprintId);

    }
    @RequestMapping(value = "/sprints/{sprintId}", method = RequestMethod.DELETE)
    public void deleteSprintById(@PathVariable Long sprintId)
    {
        sprintInterface.deleteSprintById(sprintId);
    }
}
