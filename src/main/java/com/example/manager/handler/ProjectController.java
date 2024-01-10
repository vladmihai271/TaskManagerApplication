package com.example.manager.handler;

import com.example.manager.core.application.project.ProjectInterface;
import com.example.manager.core.domain.Project;
import com.example.manager.core.domain.Sprint;
import com.example.manager.core.domain.SprintSimplified;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProjectController {
    private final ProjectInterface projectInterface;

    public ProjectController(ProjectInterface projectInterface) {
        this.projectInterface = projectInterface;
    }
    @PostMapping("/projects")
    public void postProject(@RequestBody Project project)
    {
        projectInterface.saveProject(project);
    }
    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public List<Project> getAllProjects()
    {
        return projectInterface.getProjectList();
    }
    @RequestMapping(value = "/projects/{projectId}", method = RequestMethod.GET)
    public Optional<Project> getProjectById(@PathVariable Long projectId)
    {
        return projectInterface.getProjectById(projectId);
    }
    @RequestMapping(value = "/projects/{projectId}", method = RequestMethod.PUT)
    public Optional<Project> updateProjectById(@PathVariable Long projectId, @RequestBody Project project)
    {
        return projectInterface.updateProjectById(project, projectId);
    }
    @RequestMapping(value = "/projects/{projectId}", method = RequestMethod.DELETE)
    public void deleteProjectById(@PathVariable Long projectId)
    {
        projectInterface.deleteProjectById(projectId);
    }
}
