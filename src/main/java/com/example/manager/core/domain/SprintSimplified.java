package com.example.manager.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SprintSimplified {
    private Long uid;
    private String team;
    private String tasks;
    private String status;
    private String project;
    private String title;

    public SprintSimplified(Sprint sprint) {
        this.uid = sprint.getUid();
        this.team = sprint.getTeam();
        this.tasks = sprint.getTasks();
        this.status = sprint.getStatus();
        this.project = sprint.getProject();
        this.title = sprint.getTitle();
    }
}
