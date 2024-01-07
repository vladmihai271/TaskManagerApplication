package com.example.manager.core.application.project;

import com.example.manager.core.domain.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectInterface {
    Project saveProject(Project project);

    Optional<Project> getProjectById(Long uid);
    List<Project> getProjectList();

    Optional<Project> updateProjectById(Project project, Long projectId);

    void deleteProjectById(Long uid);


}
