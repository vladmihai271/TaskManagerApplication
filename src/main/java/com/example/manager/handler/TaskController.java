package com.example.manager.handler;

import com.example.manager.core.application.employee.EmployeeInterface;
import com.example.manager.core.application.task.TaskInterface;
import com.example.manager.core.domain.Employee;
import com.example.manager.core.domain.EmployeeSimplified;
import com.example.manager.core.domain.Task;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TaskController {

    private final TaskInterface taskInterface;

    public TaskController(TaskInterface taskInterface) {
        this.taskInterface = taskInterface;
    }


    @Operation(summary = "Add a task to the database", description = "Add a task to the database. " +
            "Receives task data through the body of the request in json format. " +
            "Assigns the task to the selected employee.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully received"),
            @ApiResponse(responseCode = "404", description = "Endpoint not exposed")
    })
    @PostMapping("/tasks")
    public void postTask(@RequestBody Task task)
    {
        taskInterface.saveTask(task);
    }



    @Operation(summary = "Retrieves all tasks from the database",
            description = "Retrieves all tasks from the database as a list. " +
                    "If there are no tasks in the database returns empty list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully received"),
            @ApiResponse(responseCode = "404", description = "Endpoint not exposed")
    })
    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    public List<Task> getAllTasks()
    {
        return taskInterface.getAllTasks();
    }





    @Operation(summary = "Retrieves one task from the database using its id",
            description = "Gets one task from the database using id (the primary key). " +
                    "If no task is found with that id, Optional.empty() is returned")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully received"),
            @ApiResponse(responseCode = "404", description = "Endpoint not exposed")
    })
    @RequestMapping(value = "/tasks/{taskId}", method = RequestMethod.GET)
    public Optional<Task> getTaskById(@PathVariable Long taskId)
    {
        return taskInterface.getTaskById(taskId);
    }




    @Operation(summary = "Updates the task with the given id",
            description = "Updates the task with the given id with the" +
                    " task object sent through the body of the request. " +
                    "If no task with that id is found returns Optional.empty(). " +
                    "If the change to task affects employees that table will be updated as well.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully received"),
            @ApiResponse(responseCode = "404", description = "Endpoint not exposed")
    })
    @RequestMapping(value = "/tasks/{employeeId}", method = RequestMethod.PUT)
    public Optional<Task> updateTaskById(@PathVariable Long taskId, @RequestBody Task task)
    {
        return taskInterface.updateTaskById(taskId, task);
    }




    @Operation(summary = "Retrieves one task from the database using its title",
            description = "Gets one task from the database using title. " +
                    "If no task is found with that title, Optional.empty() is returned")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully received"),
            @ApiResponse(responseCode = "404", description = "Endpoint not exposed")
    })
    @RequestMapping(value = "/tasks/{taskTitle}", method = RequestMethod.GET)
    public Optional<Task> getTaskByTitle(@PathVariable String taskTitle)
    {
        return taskInterface.getTaskByTitle(taskTitle);
    }
    @Operation(summary = "Deletes one task by id",
            description = "Deletes one task by id. " +
                    "If task with id is not found nothing happens, returned status is 200-OK")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully received"),
            @ApiResponse(responseCode = "404", description = "Endpoint not exposed")
    })
    @RequestMapping(value = "/tasks/{taskId}", method = RequestMethod.DELETE)
    public void deleteTaskById(@PathVariable Long taskId)
    {
        taskInterface.deleteTaskById(taskId);
    }


}
