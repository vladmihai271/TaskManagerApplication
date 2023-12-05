package com.example.manager.core.application.task;

import com.example.manager.core.application.repositories.EmployeeRepository;
import com.example.manager.core.domain.Employee;
import com.example.manager.core.domain.Task;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private final EmployeeRepository employeeRepository;

    public TaskService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void addTaskToEmployee(Task task){
        Employee employeeToTask = employeeRepository.findByUsername(task.getAssignedTo());
        if(employeeToTask.getTasks().isBlank() || employeeToTask.getTasks().isEmpty()){
            employeeToTask.setTasks(task.getTitle());
        } else {
            employeeToTask.setTasks(employeeToTask.getTasks()+","+task.getTitle());
        }
    }
}
