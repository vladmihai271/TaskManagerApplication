package com.example.manager.core.domain;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employee", schema="public")
@Data
@AllArgsConstructor
public class Employee {
    @Id
    private Long uid;
    private String team;
    private String tasks;
    private String projects;
    private String availability;
    private String name;
    private String surname;
    private String username;
    private String password;
    private String securityAccess;
}
