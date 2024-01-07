package com.example.manager.core.application.repositories;

import com.example.manager.core.domain.Project;
import com.example.manager.core.domain.Sprint;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Long> {
}
