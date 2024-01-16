package com.example.manager.client.login;

import com.example.manager.core.application.employee.EmployeeService;
import com.example.manager.core.application.login.LoginInterface;
import com.example.manager.core.application.login.LoginService;
import com.example.manager.core.application.repositories.EmployeeRepository;
import com.example.manager.core.domain.Employee;
import com.example.manager.core.domain.EmployeeSimplified;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class LoginClient implements LoginInterface {
    private final EmployeeRepository employeeRepository;
    private final LoginService loginService;
    private final EmployeeService employeeService;

    public LoginClient(EmployeeRepository employeeRepository, LoginService loginService, EmployeeService employeeService) {
        this.employeeRepository = employeeRepository;
        this.loginService = loginService;
        this.employeeService = employeeService;
    }

    @Override
    public ResponseEntity<Map<String, Object>> login(String username, String password) {
        EmployeeSimplified noEmployeesStandard = new EmployeeSimplified("","available",
                    "admin","admin","admin","8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918","HR");
        if(employeeRepository.count() == 0){
            Employee adminEmployee = employeeService.saveEmployeeSimplified(noEmployeesStandard);
            Map<String,Object> map = new HashMap<>(Map.of("message", "No employees in database, default admin created"));
            map.put("userId", adminEmployee.getUid().toString());

            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        Employee loggedEmployee = employeeRepository.findByUsername(username);
        if(loggedEmployee == null) {
            return new ResponseEntity<>(Map.of("message", "No employee with this username"), HttpStatus.OK);
        }
        return loginService.checkEmployeeExists(loggedEmployee,username,password);
    }
}
