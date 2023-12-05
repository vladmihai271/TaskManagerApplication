package com.example.manager.client.login;

import com.example.manager.core.application.login.LoginInterface;
import com.example.manager.core.application.login.LoginService;
import com.example.manager.core.application.repositories.EmployeeRepository;
import com.example.manager.core.domain.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LoginClient implements LoginInterface {
    private final EmployeeRepository employeeRepository;
    private final LoginService loginService;
    public LoginClient(EmployeeRepository employeeRepository, LoginService loginService) {
        this.employeeRepository = employeeRepository;
        this.loginService = loginService;
    }

    @Override
    public ResponseEntity<Map<String, Object>> login(String username, String password) {
        Employee loggedEmployee = employeeRepository.findByUsername(username);
        return loginService.checkEmployeeExists(loggedEmployee,username,password);
    }
}
