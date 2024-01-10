package com.example.manager.client.task;

import com.example.manager.core.application.repositories.TaskRepository;
import com.example.manager.core.application.task.TaskInterface;
import com.example.manager.core.application.task.TaskService;
import com.example.manager.core.domain.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskClient implements TaskInterface {
    private final TaskRepository taskRepository;
    private final TaskService taskService;

    public TaskClient(TaskRepository taskRepository, TaskService taskService) {
        this.taskRepository = taskRepository;
        this.taskService = taskService;
    }

    @Override
    public Optional<Task> saveTask(Task task) {  //maybe should throw an exception is task title already exists
        List<Task> allTasks = this.getAllTasks();
        for(Task taskIn : allTasks){
            if(taskIn.getTitle().equals(task.getTitle())){
                return Optional.empty();
            }
        }
        taskService.addTaskToEmployee(task); //ADD TASK TO SPRINT AND/OR PROJECT WHEN IMPLEMENTED
        taskService.addTaskToSprint(task);
        return Optional.of(task);
    }

    @Override
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public Optional<Task> getTaskByTitle(String title) {
        return Optional.ofNullable(taskRepository.findByTitle(title));
    }

    @Override
    public List<Task> getAllTasks() {
        return (List<Task>) taskRepository.findAll();
    }

    @Override
    public Optional<Task> updateTaskById(Long id, Task task) {
        taskService.completeMissingFieldsFromUpdateTaskObject(id, task);
        deleteTaskById(id);
        saveTask(task);
        return Optional.empty();
    }

    @Override
    public void deleteTaskById(Long id) {
        taskService.deleteTaskFromEmployee(id);
        taskRepository.deleteById(id);
    }
}
