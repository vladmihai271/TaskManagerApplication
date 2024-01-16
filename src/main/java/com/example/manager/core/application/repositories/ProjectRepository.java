package com.example.manager.core.application.repositories;

import com.example.manager.core.domain.Employee;
import com.example.manager.core.domain.Project;
import com.example.manager.core.domain.Sprint;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Long> {
    List<Project> findAllByTeam(String team);
    Project findByTitle(String title);
}
