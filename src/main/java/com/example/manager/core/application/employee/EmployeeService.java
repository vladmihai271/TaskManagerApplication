package com.example.manager.core.application.employee;

import com.example.manager.core.application.repositories.EmployeeRepository;
import com.example.manager.core.application.repositories.TaskRepository;
import com.example.manager.core.domain.Employee;
import com.example.manager.core.domain.EmployeeSimplified;
import com.example.manager.core.domain.Task;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;

    public EmployeeService(EmployeeRepository employeeRepository, TaskRepository taskRepository) {
        this.employeeRepository = employeeRepository;
        this.taskRepository = taskRepository;
    }

    public boolean deleteEmployeeById(Long employeeId){
        Optional<Employee> employeeToBeDeleted = employeeRepository.findById(employeeId);
        if(employeeToBeDeleted.isPresent())
        {
            removeEmployeeFromHisTasks(employeeId);
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
    public Employee saveEmployeeSimplified(EmployeeSimplified employee) {
        Employee resultEmployee = fillUnusedFields(employee, Optional.empty());
        resultEmployee.setUid(System.currentTimeMillis());
        return employeeRepository.save(resultEmployee);
    }
    public Employee saveEmployee(Employee employee) {
        employee.setUid(System.currentTimeMillis());
        return employeeRepository.save(employee);
    }
    public void removeEmployeeFromHisTasks(Long employeeId){
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if(employee.isPresent()) {
            String tasks = employee.get().getTasks();
            String[] tasksTitles = tasks.split(",");
            for(int i=0;i<tasksTitles.length;i++) {
                Task task = taskRepository.findByTitle(tasksTitles[i]);
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
        if(newEmployeeReceivedAtUpdate.getAvailability().isEmpty() ||
                newEmployeeReceivedAtUpdate.getAvailability().isBlank() ||
                newEmployeeReceivedAtUpdate.getAvailability() == null)
        {
            newEmployeeReceivedAtUpdate.setAvailability(employeeToBeChanged.get().getAvailability());
        }
        if(newEmployeeReceivedAtUpdate.getTeam().isEmpty() ||
                newEmployeeReceivedAtUpdate.getTeam().isBlank() ||
                newEmployeeReceivedAtUpdate.getTeam() == null)
        {
            newEmployeeReceivedAtUpdate.setTeam(employeeToBeChanged.get().getTeam());
        }
        if(newEmployeeReceivedAtUpdate.getSurname().isEmpty() ||
                newEmployeeReceivedAtUpdate.getSurname().isBlank() ||
                newEmployeeReceivedAtUpdate.getSurname() == null)
        {
            newEmployeeReceivedAtUpdate.setSurname(employeeToBeChanged.get().getSurname());
        }
        if(newEmployeeReceivedAtUpdate.getName().isEmpty() ||
                newEmployeeReceivedAtUpdate.getName().isBlank() ||
                newEmployeeReceivedAtUpdate.getName() == null)
        {
            newEmployeeReceivedAtUpdate.setName(employeeToBeChanged.get().getName());
        }
        if(newEmployeeReceivedAtUpdate.getUsername().isEmpty() ||
                newEmployeeReceivedAtUpdate.getUsername().isBlank() ||
                newEmployeeReceivedAtUpdate.getUsername() == null)
        {
            newEmployeeReceivedAtUpdate.setUsername(employeeToBeChanged.get().getUsername());
        }
        if(newEmployeeReceivedAtUpdate.getPassword().isEmpty() ||
                newEmployeeReceivedAtUpdate.getPassword().isBlank() ||
                newEmployeeReceivedAtUpdate.getPassword() == null)
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
