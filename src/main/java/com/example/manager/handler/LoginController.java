package com.example.manager.handler;

import com.example.manager.core.application.employee.EmployeeInterface;
import com.example.manager.core.application.login.LoginInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Logs in if credentials are correct",
            description = "Checks if user exists. Returns a response accordingly. " +
                    "Status is 200-OK whether user exists or not.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully received"),
            @ApiResponse(responseCode = "404", description = "Endpoint not exposed")
    })
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> login(@RequestParam String username, @RequestParam String password)
    {
        return loginInterface.login(username, password);
    }

}
