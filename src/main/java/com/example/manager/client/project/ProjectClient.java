package com.example.manager.client.project;

import com.example.manager.core.application.project.ProjectInterface;
import com.example.manager.core.application.project.ProjectService;
import com.example.manager.core.application.repositories.ProjectRepository;
import com.example.manager.core.domain.Project;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectClient implements ProjectInterface {

    private final ProjectRepository projectRepository;
    private final ProjectService projectService;

    public ProjectClient(ProjectRepository projectRepository, ProjectService projectService) {
        this.projectRepository = projectRepository;
        this.projectService = projectService;
    }

    @Override
    public Project saveProject(Project project) {
        projectService.addProjectToMembersOfTeam(project);
        return projectRepository.save(project);
    }

    @Override
    public Optional<Project> getProjectById(Long uid) {
        return projectRepository.findById(uid);
    }

    @Override
    public List<Project> getProjectList() {
        return (List<Project>) projectRepository.findAll();
    }

    @Override
    public Optional<Project> updateProjectById(Project project, Long uid) {
        Optional<Project> projectBeforeUpdate = projectRepository.findById(uid);
        if(projectBeforeUpdate.isEmpty()){
            return Optional.empty();
        }
        project = projectService.completeMissingFieldsFromUpdateProjectObject(uid, project);
        if(!projectBeforeUpdate.get().getTeam().equals(project.getTeam())){
            projectService.deleteProjectFromMembersOfTeam(projectBeforeUpdate.get());
            projectService.addProjectToMembersOfTeam(project);
            projectService.deleteAllSprintsWithProject(projectBeforeUpdate.get());
        }
        projectRepository.deleteById(uid);
        return Optional.of(projectRepository.save(project));
    }

    @Override
    public void deleteProjectById(Long uid) {
        Optional<Project> projectBeforeDelete = projectRepository.findById(uid);
        if(projectBeforeDelete.isEmpty()){
            return;
        }
        projectService.deleteAllSprintsWithProject(projectBeforeDelete.get());
        projectService.deleteProjectFromMembersOfTeam(projectBeforeDelete.get());
        projectRepository.deleteById(uid);
    }
}
