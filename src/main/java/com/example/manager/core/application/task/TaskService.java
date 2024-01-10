package com.example.manager.core.application.task;

import com.example.manager.core.application.employee.EmployeeService;
import com.example.manager.core.application.repositories.EmployeeRepository;
import com.example.manager.core.application.repositories.SprintRepository;
import com.example.manager.core.application.repositories.TaskRepository;
import com.example.manager.core.domain.Employee;
import com.example.manager.core.domain.Sprint;
import com.example.manager.core.domain.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;
    private final EmployeeService employeeService;
    private final SprintRepository sprintRepository;

    public TaskService(EmployeeRepository employeeRepository, TaskRepository taskRepository, EmployeeService employeeService, SprintRepository sprintRepository) {
        this.employeeRepository = employeeRepository;
        this.taskRepository = taskRepository;
        this.employeeService = employeeService;
        this.sprintRepository = sprintRepository;
    }

    public void addTaskToEmployee(Task task){
        Employee employeeToTask = employeeRepository.findByUsername(task.getAssignedTo());
        if(employeeToTask.getTasks().isBlank() || employeeToTask.getTasks().isEmpty()){
            employeeToTask.setTasks(task.getTitle());
        } else {
            employeeToTask.setTasks(employeeToTask.getTasks()+","+task.getTitle());
        }
    }
    public void addTaskToSprint(Task task){
        Sprint sprintToTask = sprintRepository.findByTitle(task.getSprint());
        if(sprintToTask.getTasks().isBlank() || sprintToTask.getTasks().isEmpty()){
            sprintToTask.setTasks(task.getTitle());
        } else {
            sprintToTask.setTasks(sprintToTask.getTasks()+","+task.getTitle());
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
    public Task completeMissingFieldsFromUpdateTaskObject(Long id, Task newTaskReceivedAtUpdate){
        Optional<Task> taskToBeChanged = taskRepository.findById(id);
        if(taskToBeChanged.isEmpty()){
            return null;
        }
        if(newTaskReceivedAtUpdate.getTitle().isEmpty() ||
                newTaskReceivedAtUpdate.getTitle().isBlank() ||
                newTaskReceivedAtUpdate.getTitle() == null)
        {
            newTaskReceivedAtUpdate.setTitle(taskToBeChanged.get().getTitle());
        }
        if(newTaskReceivedAtUpdate.getStatus().isEmpty() ||
                newTaskReceivedAtUpdate.getStatus().isBlank() ||
                newTaskReceivedAtUpdate.getStatus() == null)
        {
            newTaskReceivedAtUpdate.setStatus(taskToBeChanged.get().getStatus());
        }
        if(newTaskReceivedAtUpdate.getAssignedTo().isEmpty() ||
                newTaskReceivedAtUpdate.getAssignedTo().isBlank() ||
                newTaskReceivedAtUpdate.getAssignedTo() == null)
        {
            newTaskReceivedAtUpdate.setAssignedTo(taskToBeChanged.get().getAssignedTo());
        }

        if(newTaskReceivedAtUpdate.getSprint().isEmpty() ||
                newTaskReceivedAtUpdate.getSprint().isBlank() ||
                newTaskReceivedAtUpdate.getSprint() == null)
        {
            newTaskReceivedAtUpdate.setSprint(taskToBeChanged.get().getSprint());
        }

        if(newTaskReceivedAtUpdate.getProject().isEmpty() ||
                newTaskReceivedAtUpdate.getProject().isBlank() ||
                newTaskReceivedAtUpdate.getProject() == null)
        {
            newTaskReceivedAtUpdate.setProject(taskToBeChanged.get().getProject());
        }

        if(newTaskReceivedAtUpdate.getDescription().isEmpty() ||
                newTaskReceivedAtUpdate.getDescription().isBlank() ||
                newTaskReceivedAtUpdate.getDescription() == null)
        {
            newTaskReceivedAtUpdate.setDescription(taskToBeChanged.get().getDescription());
        }

        if(newTaskReceivedAtUpdate.getStoryPoints() == null)
        {
            newTaskReceivedAtUpdate.setStoryPoints(taskToBeChanged.get().getStoryPoints());
        }
        return newTaskReceivedAtUpdate;
    }
}
