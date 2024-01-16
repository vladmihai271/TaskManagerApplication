package com.example.manager.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeSimplified {
    private String team;
    private String availability;
    private String name;
    private String surname;
    private String username;
    private String password;
    private String securityAccess;
}
