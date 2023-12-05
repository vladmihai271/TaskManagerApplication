package com.example.manager.core.application.task;

import com.example.manager.core.application.employee.EmployeeService;
import com.example.manager.core.application.repositories.EmployeeRepository;
import com.example.manager.core.application.repositories.TaskRepository;
import com.example.manager.core.domain.Employee;
import com.example.manager.core.domain.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;
    private final EmployeeService employeeService;

    public TaskService(EmployeeRepository employeeRepository, TaskRepository taskRepository, EmployeeService employeeService) {
        this.employeeRepository = employeeRepository;
        this.taskRepository = taskRepository;
        this.employeeService = employeeService;
    }

    public void addTaskToEmployee(Task task){
        Employee employeeToTask = employeeRepository.findByUsername(task.getAssignedTo());
        if(employeeToTask.getTasks().isBlank() || employeeToTask.getTasks().isEmpty()){
            employeeToTask.setTasks(task.getTitle());
        } else {
            employeeToTask.setTasks(employeeToTask.getTasks()+","+task.getTitle());
        }
    }
    public Optional<Long> findEmployeeByTaskId(Long taskId){
        Optional<Task> task = taskRepository.findById(taskId);
        if(task.isEmpty()){
            return Optional.empty();
        }
        List<Employee> allEmployees = (List<Employee>) employeeRepository.findAll();
        for(Employee employee : allEmployees){
            if(employee.getTasks().contains(task.get().getTitle())){
                return Optional.of(employee.getUid());
            }
        }
        return Optional.empty();
    }
    public void deleteTaskFromEmployee(Long taskId){
        Optional<Long> employeeId = findEmployeeByTaskId(taskId);
        Optional<Task> task = taskRepository.findById(taskId);
        Optional<Employee> employee = Optional.empty();
        if(employeeId.isPresent()) {
            employee = employeeRepository.findById(employeeId.get());
        }
        if(employeeId.isPresent() && task.isPresent() && employee.isPresent()){
            String employeeTasks = employee.get().getTasks();
            employeeTasks = employeeTasks.replace("," + task.get().getTitle(),"");
            employee.get().setTasks(employeeTasks);
            employeeRepository.deleteById(employeeId.get());
            employeeService.saveEmployee(employee.get());
        }
    }
}
