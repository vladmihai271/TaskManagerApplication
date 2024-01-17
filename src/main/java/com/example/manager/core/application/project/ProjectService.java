package com.example.manager.core.application.project;

import com.example.manager.core.application.repositories.EmployeeRepository;
import com.example.manager.core.application.repositories.ProjectRepository;
import com.example.manager.core.application.repositories.SprintRepository;
import com.example.manager.core.application.repositories.TaskRepository;
import com.example.manager.core.application.task.TaskService;
import com.example.manager.core.domain.Employee;
import com.example.manager.core.domain.Project;
import com.example.manager.core.domain.Sprint;
import com.example.manager.core.domain.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    private final EmployeeRepository employeeRepository;
    private final SprintRepository sprintRepository;
    private final TaskRepository taskRepository;
    private final TaskService taskService;
    private final ProjectRepository projectRepository;

    public ProjectService(EmployeeRepository employeeRepository, SprintRepository sprintRepository, TaskRepository taskRepository, TaskService taskService, ProjectRepository projectRepository) {
        this.employeeRepository = employeeRepository;
        this.sprintRepository = sprintRepository;
        this.taskRepository = taskRepository;
        this.taskService = taskService;
        this.projectRepository = projectRepository;
    }
    public void deleteAllSprintsWithProject(Project project) {
        List<Sprint> sprints = sprintRepository.findAllByProject(project.getTitle());
        for(Sprint sprint : sprints){
            if(sprint.getProject().equals(project.getTitle())){
                sprintRepository.deleteById(sprint.getUid());
                List<Task> tasks = taskRepository.findAllBySprint(sprint.getTitle());
                for(Task task : tasks){
                    if(task.getSprint().equals(sprint.getTitle())){
                        taskService.deleteTaskFromEmployee(task.getUid());
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
            if(employee.getProjects().contains(","+project.getTitle() + ",")) {
                employee.setProjects(employee.getProjects().replace(project.getTitle() + ",", ""));
            } else if (employee.getProjects().contains("," + project.getTitle())) {
                employee.setProjects(employee.getProjects().replace("," + project.getTitle(), ""));
            } else if (employee.getProjects().contains(project.getTitle() + ",")) {
                employee.setProjects(employee.getProjects().replace(project.getTitle() + ",", ""));
            } else if (employee.getProjects().contains(project.getTitle())) {
                employee.setProjects(employee.getProjects().replace(project.getTitle(), ""));
            }
        }
    }
    public Project completeMissingFieldsFromUpdateProjectObject(Long id, Project newProjectReceivedAtUpdate){
        Optional<Project> projectToBeChanged = projectRepository.findById(id);
        if(projectToBeChanged.isEmpty()){
            return null;
        }
        if(newProjectReceivedAtUpdate.getTitle() == null || newProjectReceivedAtUpdate.getTitle().isEmpty() ||
                newProjectReceivedAtUpdate.getTitle().isBlank())
        {
            newProjectReceivedAtUpdate.setTitle(projectToBeChanged.get().getTitle());
        }
        if(newProjectReceivedAtUpdate.getStatus() == null || newProjectReceivedAtUpdate.getStatus().isEmpty() ||
                newProjectReceivedAtUpdate.getStatus().isBlank())
        {
            newProjectReceivedAtUpdate.setStatus(projectToBeChanged.get().getStatus());
        }
        if(newProjectReceivedAtUpdate.getDescription() == null || newProjectReceivedAtUpdate.getDescription().isEmpty() ||
                newProjectReceivedAtUpdate.getDescription().isBlank())
        {
            newProjectReceivedAtUpdate.setDescription(projectToBeChanged.get().getDescription());
        }
        if(newProjectReceivedAtUpdate.getTeam() == null || newProjectReceivedAtUpdate.getTeam().isEmpty() ||
                newProjectReceivedAtUpdate.getTeam().isBlank())
        {
            newProjectReceivedAtUpdate.setTeam(projectToBeChanged.get().getTeam());
        }
        if(newProjectReceivedAtUpdate.getUid() == null)
        {
            newProjectReceivedAtUpdate.setUid(projectToBeChanged.get().getUid());
        }
        if(newProjectReceivedAtUpdate.isHidden())
        {
            newProjectReceivedAtUpdate.setHidden(true);
        } else {
            newProjectReceivedAtUpdate.setHidden(false);
        }
        return newProjectReceivedAtUpdate;
    }
}
