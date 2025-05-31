package com.news.app.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.news.app.domain.modal.User;                 // ✅ Should exist
import com.news.app.domain.services.AuthService;       // ✅ Should exist
import com.news.app.web.dto.LoginRequest;              // ✅ Should exist

@RestController
@RequestMapping("/api/auth")    
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = authService.authenticate(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(user);
    }
}
