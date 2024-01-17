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
        Employee employeeToTask = employeeRepository.findByUsername(task.getAssignee());
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
            if(employeeTasks.contains("," + task.get().getTitle()+",")) {
                employeeTasks = employeeTasks.replace(task.get().getTitle()+",","");
            } else if(employeeTasks.contains(task.get().getTitle()+",")){
                employeeTasks = employeeTasks.replace(task.get().getTitle()+",","");
            } else if(employeeTasks.contains("," + task.get().getTitle())){
                employeeTasks = employeeTasks.replace("," + task.get().getTitle(),"");
            } else if(employeeTasks.contains(task.get().getTitle())){
                employeeTasks = employeeTasks.replace(task.get().getTitle(),"");
            }
            employee.get().setTasks(employeeTasks);
            employeeRepository.deleteById(employeeId.get());
            employeeService.saveEmployee(employee.get());
        }
    }
    public Optional<Long> findSprintByTaskId(Long taskId){
        Optional<Task> task = taskRepository.findById(taskId);
        if(task.isEmpty()){
            return Optional.empty();
        }
        List<Sprint> allSprints = (List<Sprint>) sprintRepository.findAll();
        for(Sprint sprint : allSprints){
            if(sprint.getTasks().contains(task.get().getTitle())){
                return Optional.of(sprint.getUid());
            }
        }
        return Optional.empty();
    }
    public void deleteTaskFromSprint(Long taskId){
        Optional<Long> sprintId = findSprintByTaskId(taskId);
        Optional<Task> task = taskRepository.findById(taskId);
        Optional<Sprint> sprint = Optional.empty();
        if(sprintId.isPresent()) {
            sprint = sprintRepository.findById(sprintId.get());
        }
        if(sprintId.isPresent() && task.isPresent() && sprint.isPresent()){
            String sprintTasks = sprint.get().getTasks();
            if(sprintTasks.contains("," + task.get().getTitle()+",")) {
                sprintTasks = sprintTasks.replace(task.get().getTitle()+",","");
            } else if(sprintTasks.contains(task.get().getTitle()+",")){
                sprintTasks = sprintTasks.replace(task.get().getTitle()+",","");
            } else if(sprintTasks.contains("," + task.get().getTitle())){
                sprintTasks = sprintTasks.replace("," + task.get().getTitle(),"");
            } else if(sprintTasks.contains(task.get().getTitle())){
                sprintTasks = sprintTasks.replace(task.get().getTitle(),"");
            }
            sprint.get().setTasks(sprintTasks);
            sprintRepository.deleteById(sprintId.get());
            sprintRepository.save(sprint.get());
        }
    }

    public Task completeMissingFieldsFromUpdateTaskObject(Long id, Task newTaskReceivedAtUpdate){
        Optional<Task> taskToBeChanged = taskRepository.findById(id);
        if(taskToBeChanged.isEmpty()){
            return null;
        }
        if(newTaskReceivedAtUpdate.getTitle() == null || newTaskReceivedAtUpdate.getTitle().isEmpty() ||
                newTaskReceivedAtUpdate.getTitle().isBlank())
        {
            newTaskReceivedAtUpdate.setTitle(taskToBeChanged.get().getTitle());
        }
        if(newTaskReceivedAtUpdate.getStatus() == null || newTaskReceivedAtUpdate.getStatus().isEmpty() ||
                newTaskReceivedAtUpdate.getStatus().isBlank())
        {
            newTaskReceivedAtUpdate.setStatus(taskToBeChanged.get().getStatus());
        }
        if(newTaskReceivedAtUpdate.getAssignee() == null || newTaskReceivedAtUpdate.getAssignee().isEmpty() ||
                newTaskReceivedAtUpdate.getAssignee().isBlank())
        {
            newTaskReceivedAtUpdate.setAssignee(taskToBeChanged.get().getAssignee());
        }

        if(newTaskReceivedAtUpdate.getSprint() == null || newTaskReceivedAtUpdate.getSprint().isEmpty() ||
                newTaskReceivedAtUpdate.getSprint().isBlank())
        {
            newTaskReceivedAtUpdate.setSprint(taskToBeChanged.get().getSprint());
        }

        if(newTaskReceivedAtUpdate.getProject() == null || newTaskReceivedAtUpdate.getProject().isEmpty() ||
                newTaskReceivedAtUpdate.getProject().isBlank())
        {
            newTaskReceivedAtUpdate.setProject(taskToBeChanged.get().getProject());
        }

        if(newTaskReceivedAtUpdate.getDescription() == null || newTaskReceivedAtUpdate.getDescription().isEmpty() ||
                newTaskReceivedAtUpdate.getDescription().isBlank())
        {
            newTaskReceivedAtUpdate.setDescription(taskToBeChanged.get().getDescription());
        }
        if(newTaskReceivedAtUpdate.getUid() == null)
        {
            newTaskReceivedAtUpdate.setUid(taskToBeChanged.get().getUid());
        }
        if(newTaskReceivedAtUpdate.getStoryPoints() == null)
        {
            newTaskReceivedAtUpdate.setStoryPoints(taskToBeChanged.get().getStoryPoints());
        }
        return newTaskReceivedAtUpdate;
    }
}
