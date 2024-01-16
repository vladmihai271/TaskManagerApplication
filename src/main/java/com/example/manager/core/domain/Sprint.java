package com.example.manager.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sprint", schema="public")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sprint {
    @Id
    private Long uid;
    private String team;
    private String tasks;
    private String status;
    private String project;
    private String title;

    public Sprint(SprintSimplified sprintSimplified) {
        this.uid = sprintSimplified.getUid();
        this.team = sprintSimplified.getTeam();
        this.tasks = "";
        this.status = sprintSimplified.getStatus();
        this.project = sprintSimplified.getProject();
        this.title = sprintSimplified.getTitle();
    }
}
