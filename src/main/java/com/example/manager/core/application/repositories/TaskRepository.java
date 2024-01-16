package com.example.manager.core.application.repositories;

import com.example.manager.core.domain.Sprint;
import com.example.manager.core.domain.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task,Long> {
    Task findByTitle(String username);
    List<Task> findAllBySprint(String sprint);

}
