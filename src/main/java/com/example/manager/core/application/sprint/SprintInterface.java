package com.example.manager.core.application.sprint;

import com.example.manager.core.domain.Employee;
import com.example.manager.core.domain.EmployeeSimplified;
import com.example.manager.core.domain.Sprint;
import com.example.manager.core.domain.SprintSimplified;

import java.util.List;
import java.util.Optional;

public interface SprintInterface {
    // save operation
    Optional<Sprint> saveSprintSimplified(SprintSimplified sprint);

    // read operation
    List<Sprint> getSprintList(Long userId);
    Optional<Sprint> getSprintById(Long sprintId, Long userId);

    public Optional<Sprint> findSprintByTitle(String title);

    // update operation
    Optional<Sprint> updateSprintById(SprintSimplified employee, Long sprintId);

    // delete operation
    void deleteSprintById(Long sprintId);
}
