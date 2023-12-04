package com.example.manager.core.application.login;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface LoginInterface {
    ResponseEntity<Map<String,Object>> login(String username, String password);
}
