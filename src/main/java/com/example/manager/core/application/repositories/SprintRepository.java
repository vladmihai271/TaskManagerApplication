package com.example.manager.core.application.repositories;

import com.example.manager.core.domain.Sprint;
import com.example.manager.core.domain.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SprintRepository extends CrudRepository<Sprint,Long> {
    Sprint findByTitle(String title);
}
