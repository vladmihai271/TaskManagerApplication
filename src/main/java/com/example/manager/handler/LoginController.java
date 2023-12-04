package com.example.manager.handler;

import com.example.manager.core.application.employee.EmployeeInterface;
import com.example.manager.core.application.login.LoginInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {
    private final LoginInterface loginInterface;

    public LoginController(EmployeeInterface employeeInterface, LoginInterface loginInterface) {
        this.loginInterface = loginInterface;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> login(@RequestParam String username, @RequestParam String password)
    {
        return loginInterface.login(username, password);
    }

}
