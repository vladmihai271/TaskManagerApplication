package com.example.manager.core.application.repositories;

import com.example.manager.core.domain.Project;
import com.example.manager.core.domain.Sprint;
import com.example.manager.core.domain.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SprintRepository extends CrudRepository<Sprint,Long> {
    Sprint findByTitle(String title);
    List<Sprint> findAllByProject(String project);
}
