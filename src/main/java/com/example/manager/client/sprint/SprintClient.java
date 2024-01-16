package com.example.manager.client.sprint;

import com.example.manager.core.application.repositories.EmployeeRepository;
import com.example.manager.core.application.repositories.ProjectRepository;
import com.example.manager.core.application.repositories.SprintRepository;
import com.example.manager.core.application.sprint.SprintInterface;
import com.example.manager.core.application.sprint.SprintService;
import com.example.manager.core.domain.Employee;
import com.example.manager.core.domain.Project;
import com.example.manager.core.domain.Sprint;
import com.example.manager.core.domain.SprintSimplified;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class SprintClient implements SprintInterface {

    private final SprintRepository sprintRepository;
    private final SprintService sprintService;
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;

    public SprintClient(SprintRepository sprintRepository, SprintService sprintService, EmployeeRepository employeeRepository, ProjectRepository projectRepository) {
        this.sprintRepository = sprintRepository;
        this.sprintService = sprintService;
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public Optional<Sprint> saveSprintSimplified(SprintSimplified sprint) {
        return sprintService.saveSprint(sprint);

    }

    @Override
    public List<Sprint> getSprintList(Long userId) {
        Optional<Employee> employee = employeeRepository.findById(userId);
        if(employee.isPresent() && employee.get().getSecurityAccess().equals("Department Chief")){
            return (List<Sprint>) sprintRepository.findAll();
        }
        if(employee.isPresent() && !employee.get().getSecurityAccess().equals("Department Chief")){
            List<Sprint> sprints = (List<Sprint>) sprintRepository.findAll();
            List<Sprint> sprintsResult = new ArrayList<>();
            for(Sprint sprint : sprints){
                Project project = projectRepository.findByTitle(sprint.getProject());
                if(!project.isHidden()) {
                    sprintsResult.add(sprint);
                } else if (project.isHidden() && project.getTeam().equals(employee.get().getTeam())){
                    sprintsResult.add(sprint);

                }
            }
            return sprintsResult;
        }
        return new ArrayList<>();
    }

    @Override
    public Optional<Sprint> getSprintById(Long sprintId, Long userId) {
        Optional<Employee> employee = employeeRepository.findById(userId);
        if(employee.isPresent() && employee.get().getSecurityAccess().equals("Department Chief")){
            return sprintRepository.findById(sprintId);
        }
        Optional<Sprint> sprint = sprintRepository.findById(sprintId);
        if(employee.isPresent() && sprint.isPresent() && employee.get().getTeam().equals(sprint.get().getTeam())){
            return sprint;
        }
        if(sprint.isPresent()) {
            Project project = projectRepository.findByTitle(sprint.get().getProject());
            if (project.isHidden())
            {
                return Optional.empty();
            }
        }
        return sprint;
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
