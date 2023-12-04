package com.example.manager.core.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "employee", schema="public")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

}
