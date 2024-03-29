package com.example.manager.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "project", schema="public")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    @Id
    private Long uid;
    private String team;
    private String status;
    private String title;
    private String description;
    private boolean hidden;
}
