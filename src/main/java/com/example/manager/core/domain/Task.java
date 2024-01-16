package com.example.manager.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "task", schema="public")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    private Long uid;
    private String title;
    private String description;
    private String sprint;
    private String status;
    private String project;
    private String assignee;
    private Integer storyPoints;
}
