package com.example.manager.handler;

import com.example.manager.core.application.employee.EmployeeInterface;
import com.example.manager.core.application.project.ProjectInterface;
import com.example.manager.core.domain.Employee;
import com.example.manager.core.domain.Project;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ProjectController {
    private final ProjectInterface projectInterface;
    private final EmployeeInterface employeeInterface;

    public ProjectController(ProjectInterface projectInterface, EmployeeInterface employeeInterface) {
        this.projectInterface = projectInterface;
        this.employeeInterface = employeeInterface;
    }
    @PostMapping("/projects/{userId}")
    public void postProject(@RequestBody Project project, @PathVariable Long userId)
    {
        Optional<Employee> userEmployee = employeeInterface.getEmployeeById(userId);
        if(userEmployee.isPresent() && userEmployee.get().getSecurityAccess().equals("Department Chief")) {
            projectInterface.saveProject(project);
        }
    }
    @RequestMapping(value = "/projects/{userId}", method = RequestMethod.GET)
    public List<Project> getAllProjects(@PathVariable Long userId)
    {
        Optional<Employee> userEmployee = employeeInterface.getEmployeeById(userId);
        List<Project> projects = projectInterface.getProjectList();
        List<Project> visibleProjects = new ArrayList<>();
        for (Project project : projects) {
            if (userEmployee.isPresent() && userEmployee.get().getSecurityAccess().equals("Department Chief")
                    && project.isHidden()) {
                visibleProjects.add(project);
            } else if (userEmployee.isPresent()
                    && project.isHidden() && project.getTeam().equals(userEmployee.get().getTeam())) {
                visibleProjects.add(project);
            } else if(userEmployee.isPresent() && !project.isHidden()) {
                visibleProjects.add(project);
            }

        }
        return visibleProjects;
    }
    @RequestMapping(value = "/projects/{projectId}/{userId}", method = RequestMethod.GET)
    public Optional<Project> getProjectById(@PathVariable Long projectId, @PathVariable Long userId) {
        Optional<Employee> userEmployee = employeeInterface.getEmployeeById(userId);
        Optional<Project> project = projectInterface.getProjectById(projectId);
        if (userEmployee.isPresent() && userEmployee.get().getSecurityAccess().equals("Department Chief")
            && project.isPresent() && project.get().isHidden()) {
            return project;
        }
        if (userEmployee.isPresent() && project.isPresent()
                && project.get().isHidden() && project.get().getTeam().equals(userEmployee.get().getTeam())) {
            return project;
        }
        if(userEmployee.isPresent() && !project.get().isHidden()) {
            return project;
        }
        return Optional.empty();
    }
    @RequestMapping(value = "/projects/{projectId}/{userId}", method = RequestMethod.PUT)
    public Optional<Project> updateProjectById(@PathVariable Long projectId, @RequestBody Project project,
                                               @PathVariable Long userId)
    {
        Optional<Employee> userEmployee = employeeInterface.getEmployeeById(userId);
        if(userEmployee.isPresent() && userEmployee.get().getSecurityAccess().equals("Department Chief")) {
            return projectInterface.updateProjectById(project, projectId);
        }
        return Optional.empty();
    }
    @RequestMapping(value = "/projects/{projectId}/{userId}", method = RequestMethod.DELETE)
    public void deleteProjectById(@PathVariable Long projectId, @PathVariable Long userId)
    {
        Optional<Employee> userEmployee = employeeInterface.getEmployeeById(userId);
        if(userEmployee.isPresent() && userEmployee.get().getSecurityAccess().equals("Department Chief")) {
            projectInterface.deleteProjectById(projectId);
        }
    }
}
