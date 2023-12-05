package com.example.manager.core.application.task;

import com.example.manager.core.domain.Task;

import java.util.List;
import java.util.Optional;

public interface TaskInterface {
    Optional<Task> saveTask(Task task);
    Optional<Task> getTaskById(Long id);
    Optional<Task> getTaskByTitle(String title);
    List<Task> getAllTasks();
    Optional<Task> updateTaskById(Long id, Task task);
    void deleteTaskById(Long id);
}
