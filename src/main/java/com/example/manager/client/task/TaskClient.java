package com.example.manager.client.task;

import com.example.manager.core.application.task.TaskInterface;
import com.example.manager.core.domain.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskClient implements TaskInterface {
    @Override
    public Task saveTask(Task task) {
        return null;
    }

    @Override
    public Optional<Task> getTaskById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Task> getTaskByTitle(String title) {
        return Optional.empty();
    }

    @Override
    public List<Task> getAllTasks() {
        return null;
    }

    @Override
    public Optional<Task> updateTaskById(Long id, Task task) {
        return Optional.empty();
    }

    @Override
    public void deleteTaskById(Long id) {

    }
}
