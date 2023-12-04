package com.example.manager.core.application.login;

import com.example.manager.core.domain.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {
    public ResponseEntity<Map<String, Object>> checkEmployeeExists(Employee loggedEmployee, String username, String password)
    {
        if(loggedEmployee.getPassword().equals(password) && loggedEmployee.getUsername().equals(username)) {
            Map<String,Object> successfulLoginResponse = new HashMap<>();
            successfulLoginResponse.put("Authenticated",(boolean) true);
            successfulLoginResponse.put("Authentication message", (String) "Credentials are correct");
            return new ResponseEntity<>(successfulLoginResponse, HttpStatus.OK);
        }
        else {
            Map<String,Object> unsuccessfulLoginResponse = new HashMap<>();
            unsuccessfulLoginResponse.put("Authenticated",(boolean) false);
            unsuccessfulLoginResponse.put("Authentication message", (String) "Credentials are incorrect");
            return new ResponseEntity<>(unsuccessfulLoginResponse, HttpStatus.OK);
        }
    }
}
