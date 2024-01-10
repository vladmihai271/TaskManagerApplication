package com.example.manager.client.project;

import com.example.manager.core.application.project.ProjectInterface;
import com.example.manager.core.application.repositories.ProjectRepository;
import com.example.manager.core.domain.Project;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectClient implements ProjectInterface {

    private final ProjectRepository projectRepository;

    public ProjectClient(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Project saveProject(Project project) {
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
    public Optional<Project> updateProjectById(Project project, Long projectId) {
        projectRepository.deleteById(projectId);
        return Optional.of(projectRepository.save(project));
    }

    @Override
    public void deleteProjectById(Long uid) {
        projectRepository.deleteById(uid);
    }
}
