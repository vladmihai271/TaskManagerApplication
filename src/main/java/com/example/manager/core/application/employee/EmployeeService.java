package com.example.manager.core.application.employee;

import com.example.manager.core.application.repositories.EmployeeRepository;
import com.example.manager.core.application.repositories.ProjectRepository;
import com.example.manager.core.application.repositories.TaskRepository;
import com.example.manager.core.domain.Employee;
import com.example.manager.core.domain.EmployeeSimplified;
import com.example.manager.core.domain.Project;
import com.example.manager.core.domain.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public EmployeeService(EmployeeRepository employeeRepository, TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.employeeRepository = employeeRepository;
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    public boolean deleteEmployeeById(Long employeeId, boolean update){
        Optional<Employee> employeeToBeDeleted = employeeRepository.findById(employeeId);
        if(employeeToBeDeleted.isPresent())
        {
            if(!update) {
                removeEmployeeFromHisTasks(employeeId);
            }
            employeeRepository.deleteById(employeeId);
            return true;
        }
        else
        {
            return false;
        }
    }
    public Employee fillUnusedFields(EmployeeSimplified employeeSimplified, Optional<Long> id){
        if(id.isPresent()){
        return new Employee(id.get(), employeeSimplified.getTeam(),
                "","", employeeSimplified.getAvailability(),
                employeeSimplified.getName(), employeeSimplified.getSurname(),
                employeeSimplified.getUsername(), employeeSimplified.getPassword(),
                employeeSimplified.getSecurityAccess());
        }
        else
        {
            return new Employee(0L, employeeSimplified.getTeam(),
                    "","", employeeSimplified.getAvailability(),
                    employeeSimplified.getName(), employeeSimplified.getSurname(),
                    employeeSimplified.getUsername(), employeeSimplified.getPassword(),
                    employeeSimplified.getSecurityAccess());
        }
    }
    public void addProjectsToNewEmployee(Employee employee) {
        if(!(employee.getTeam().isBlank() || employee.getTeam().isEmpty())){
            List<Project> projects = projectRepository.findAllByTeam(employee.getTeam());
            for(Project project : projects){
                if(employee.getProjects().isBlank() || employee.getProjects().isEmpty()){
                    employee.setProjects(project.getTitle());
                } else {
                    employee.setProjects(employee.getProjects()+","+project.getTitle());
                }
            }
        }
    }
    public Employee saveEmployeeSimplified(EmployeeSimplified employee) {
        Employee resultEmployee = fillUnusedFields(employee, Optional.empty());
        resultEmployee.setUid(System.currentTimeMillis());
        addProjectsToNewEmployee(resultEmployee);
        return employeeRepository.save(resultEmployee);
    }
    public Employee saveEmployee(Employee employee) {
        employee.setUid(System.currentTimeMillis());
        addProjectsToNewEmployee(employee);
        return employeeRepository.save(employee);
    }
    public void removeEmployeeFromHisTasks(Long employeeId){
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if(employee.isPresent()) {
            String tasks = employee.get().getTasks();
            String[] tasksTitles = tasks.split(",");
            if(tasksTitles[0]!=null && tasksTitles[0].equals("")){
                return;
            }
            for (String tasksTitle : tasksTitles) {
                Task task = taskRepository.findByTitle(tasksTitle);
                task.setAssignee("");
                taskRepository.deleteById(task.getUid());
                taskRepository.save(task);
            }
        }
    }

    public Employee completeMissingFieldsFromUpdateEmployeeObject(Long id, EmployeeSimplified newEmployeeReceivedAtUpdate){
        Optional<Employee> employeeToBeChanged = employeeRepository.findById(id);
        if(employeeToBeChanged.isEmpty()){
            return null;
        }
        if(newEmployeeReceivedAtUpdate.getAvailability() == null ||
        newEmployeeReceivedAtUpdate.getAvailability().isEmpty() ||
                newEmployeeReceivedAtUpdate.getAvailability().isBlank())
        {
            newEmployeeReceivedAtUpdate.setAvailability(employeeToBeChanged.get().getAvailability());
        }
        if(newEmployeeReceivedAtUpdate.getTeam() == null || newEmployeeReceivedAtUpdate.getTeam().isEmpty() ||
                newEmployeeReceivedAtUpdate.getTeam().isBlank())
        {
            newEmployeeReceivedAtUpdate.setTeam(employeeToBeChanged.get().getTeam());
        }
        if(newEmployeeReceivedAtUpdate.getSurname() == null || newEmployeeReceivedAtUpdate.getSurname().isEmpty() ||
                newEmployeeReceivedAtUpdate.getSurname().isBlank())
        {
            newEmployeeReceivedAtUpdate.setSurname(employeeToBeChanged.get().getSurname());
        }
        if(newEmployeeReceivedAtUpdate.getName() == null || newEmployeeReceivedAtUpdate.getName().isEmpty() ||
                newEmployeeReceivedAtUpdate.getName().isBlank())
        {
            newEmployeeReceivedAtUpdate.setName(employeeToBeChanged.get().getName());
        }
        if(newEmployeeReceivedAtUpdate.getUsername() == null || newEmployeeReceivedAtUpdate.getUsername().isEmpty() ||
                newEmployeeReceivedAtUpdate.getUsername().isBlank())
        {
            newEmployeeReceivedAtUpdate.setUsername(employeeToBeChanged.get().getUsername());
        }
        if( newEmployeeReceivedAtUpdate.getPassword() == null || newEmployeeReceivedAtUpdate.getPassword().isEmpty() ||
                newEmployeeReceivedAtUpdate.getPassword().isBlank())
        {
            newEmployeeReceivedAtUpdate.setPassword(employeeToBeChanged.get().getPassword());
        }
        employeeToBeChanged.get().setPassword(newEmployeeReceivedAtUpdate.getPassword());
        employeeToBeChanged.get().setTeam(newEmployeeReceivedAtUpdate.getTeam());
        employeeToBeChanged.get().setAvailability(newEmployeeReceivedAtUpdate.getAvailability());
        employeeToBeChanged.get().setSurname(newEmployeeReceivedAtUpdate.getSurname());
        employeeToBeChanged.get().setUsername(newEmployeeReceivedAtUpdate.getUsername());
        employeeToBeChanged.get().setName(newEmployeeReceivedAtUpdate.getName());

        return employeeToBeChanged.get();
    }
}
