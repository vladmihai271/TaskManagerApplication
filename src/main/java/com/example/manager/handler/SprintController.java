package com.example.manager.handler;

import com.example.manager.core.application.employee.EmployeeInterface;
import com.example.manager.core.application.sprint.SprintInterface;
import com.example.manager.core.domain.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class SprintController {
    private final SprintInterface sprintInterface;
    private final EmployeeInterface employeeInterface;
    public SprintController(SprintInterface sprintInterface, EmployeeInterface employeeInterface) {
        this.sprintInterface = sprintInterface;
        this.employeeInterface = employeeInterface;
    }
    @PostMapping("/sprints/{userId}")
    public void postSprint(@RequestBody SprintSimplified sprint, @PathVariable Long userId)
    {
        Optional<Employee> employee = employeeInterface.getEmployeeById(userId);
        String[] projects = employee.get().getProjects().split("\\s*,\\s*");

        if(employee.isPresent() && (employee.get().getSecurityAccess().equals("Department Chief") ||
                employee.get().getSecurityAccess().equals("Team Leader"))) {
            sprintInterface.saveSprintSimplified(sprint);
        }
    }
    @RequestMapping(value = "/sprints/{userId}", method = RequestMethod.GET)
    public List<Sprint> getAllSprints(@PathVariable Long userId)
    {
        return sprintInterface.getSprintList(userId);
    }
    @RequestMapping(value = "/sprints/{sprintId}/{userId}", method = RequestMethod.GET)
    public Optional<Sprint> getSprintById(@PathVariable Long sprintId, @PathVariable Long userId)
    {
        return sprintInterface.getSprintById(sprintId, userId);
    }
    @RequestMapping(value = "/sprints/{sprintId}/{userId}", method = RequestMethod.PUT)
    public Optional<Sprint> updateSprintById(@PathVariable Long sprintId, @RequestBody SprintSimplified sprintSimplified,
                                             @PathVariable Long userId)
    {
        Optional<Employee> employee = employeeInterface.getEmployeeById(userId);
        if(employee.isPresent() && (employee.get().getSecurityAccess().equals("Department Chief") ||
                employee.get().getSecurityAccess().equals("Team Leader"))) {
            return sprintInterface.updateSprintById(sprintSimplified, sprintId);
        }
        return Optional.empty();
    }
    @RequestMapping(value = "/sprints/{sprintId}/{userId}", method = RequestMethod.DELETE)
    public void deleteSprintById(@PathVariable Long sprintId, @PathVariable Long userId)
    {
        Optional<Employee> employee = employeeInterface.getEmployeeById(userId);
        if(employee.isPresent() && (employee.get().getSecurityAccess().equals("Department Chief") ||
                employee.get().getSecurityAccess().equals("Team Leader"))) {
            sprintInterface.deleteSprintById(sprintId);
        }
    }
}
