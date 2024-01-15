package com.example.manager.core.application.login;

import com.example.manager.core.domain.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {
    private LoginService loginService;
    @BeforeEach
    public void beforeEach(){
        loginService = new LoginService();
    }
    @Test
    public void checkEmployeeExistsTestSuccess() {
        Employee employee = new Employee(1L, "team", "tasks,",
                "projects","availability", "name", "surname", "username", "password", "securityAccess");
        assertThat(loginService.checkEmployeeExists(employee, "username", "password")).
                isEqualTo(new ResponseEntity<>(Map.of("Authenticated", true, "Authentication message",
                        "Credentials are correct"), HttpStatus.OK));
    }
    @Test
    public void checkEmployeeExistsTestFailure() {
        Employee employee = new Employee(1L, "team", "tasks,",
                "projects","availability", "name", "surname", "username", "password", "securityAccess");
        assertThat(loginService.checkEmployeeExists(employee, "username", "wrongPassword")).
                isEqualTo(new ResponseEntity<>(Map.of("Authenticated", false, "Authentication message",
                        "Credentials are incorrect"), HttpStatus.OK));
    }
}
