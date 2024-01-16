package com.example.manager.core.application.project;

import com.example.manager.core.application.repositories.EmployeeRepository;
import com.example.manager.core.application.repositories.SprintRepository;
import com.example.manager.core.application.repositories.TaskRepository;
import com.example.manager.core.domain.Employee;
import com.example.manager.core.domain.Project;
import com.example.manager.core.domain.Sprint;
import com.example.manager.core.domain.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    private final EmployeeRepository employeeRepository;
    private final SprintRepository sprintRepository;
    private final TaskRepository taskRepository;

    public ProjectService(EmployeeRepository employeeRepository, SprintRepository sprintRepository, TaskRepository taskRepository) {
        this.employeeRepository = employeeRepository;
        this.sprintRepository = sprintRepository;
        this.taskRepository = taskRepository;
    }
    public void deleteAllSprintsWithProject(Project project) {
        List<Sprint> sprints = sprintRepository.findAllByProject(project.getTitle());
        for(Sprint sprint : sprints){
            if(sprint.getProject().equals(project.getTitle())){
                sprintRepository.deleteById(sprint.getUid());
                List<Task> tasks = taskRepository.findAllBySprint(sprint.getTitle());
                for(Task task : tasks){
                    if(task.getSprint().equals(sprint.getTitle())){
                        taskRepository.deleteById(task.getUid());
                    }
                }
            }
        }
    }
    public void addProjectToMembersOfTeam(Project project) {
        String team = project.getTeam();
        List<Employee> employees = employeeRepository.findAllByTeam(team);
        for(Employee employee : employees){
            if(employee.getProjects().isBlank() || employee.getProjects().isEmpty()){
                employee.setProjects(project.getTitle());
            } else {
                employee.setProjects(employee.getProjects()+","+project.getTitle());
            }
        }
    }
    public void deleteProjectFromMembersOfTeam(Project project) {
        String team = project.getTeam();
        List<Employee> employees = employeeRepository.findAllByTeam(team);
        for(Employee employee : employees) {
            if (employee.getProjects().contains(project.getTitle() + ",")) {
                employee.setProjects(employee.getProjects().replace(project.getTitle() + ",", ""));
            } else if (employee.getProjects().contains(project.getTitle())) {
                employee.setProjects(employee.getProjects().replace(project.getTitle(), ""));
            }
        }
    }
}
