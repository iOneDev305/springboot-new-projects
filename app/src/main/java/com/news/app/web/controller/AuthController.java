package com.news.app.web.controller;

import com.news.app.domain.modal.ApiResponse;
import com.news.app.domain.modal.User;
import com.news.app.domain.repository.UserRepository;
import com.news.app.infrastructure.security.JwtTokenProvider;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        logger.info("Login attempt for user: {}", loginRequest.getUsername());

        try {
            // Check if user exists
            User user = userRepository.findByUsername(loginRequest.getUsername())
                    .orElse(null);

            if (user == null) {
                logger.warn("Login failed: User not found - {}", loginRequest.getUsername());
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error(400, "User not found"));
            }

            logger.debug("Found user: {}", user.getUsername());

            // Verify password
            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                logger.warn("Login failed: Invalid password for user - {}", loginRequest.getUsername());
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error(400, "Invalid password"));
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.generateToken(authentication);

            logger.info("Login successful for user: {}", loginRequest.getUsername());
            return ResponseEntity.ok(ApiResponse.success(new JwtAuthenticationResponse(jwt)));
        } catch (BadCredentialsException e) {
            logger.error("Login failed: Bad credentials for user: {} - {}", loginRequest.getUsername(), e.getMessage());
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(400, "Invalid username or password"));
        } catch (Exception e) {
            logger.error("Login failed for user: {} - Error: {}", loginRequest.getUsername(), e.getMessage());
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(400, "Authentication failed: " + e.getMessage()));
        }
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        logger.info("Registration attempt for user: {}", signUpRequest.getUsername());

        try {
            if (userRepository.existsByUsername(signUpRequest.getUsername())) {
                logger.warn("Registration failed: Username already taken - {}", signUpRequest.getUsername());
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error(400, "Username is already taken!"));
            }

            if (userRepository.existsByEmail(signUpRequest.getEmail())) {
                logger.warn("Registration failed: Email already in use - {}", signUpRequest.getEmail());
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error(400, "Email is already in use!"));
            }

            User user = new User();
            user.setUsername(signUpRequest.getUsername());
            user.setEmail(signUpRequest.getEmail());
            user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

            User savedUser = userRepository.save(user);
            logger.info("User registered successfully: {} with ID: {}", savedUser.getUsername(), savedUser.getId());

            return ResponseEntity.ok(ApiResponse.success("User registered successfully"));
        } catch (Exception e) {
            logger.error("Registration failed: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(400, "Registration failed: " + e.getMessage()));
        }
    }

    @GetMapping("/index")
    public ResponseEntity<?> getAllUsers() {
        logger.info("Fetching all users");

        try {
            List<User> users = userRepository.findAll();
            logger.info("Found {} users", users.size());
            return ResponseEntity.ok(ApiResponse.success(users));
        } catch (Exception e) {
            logger.error("Error fetching users: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(400, "Error fetching users: " + e.getMessage()));
        }
    }
}

@Data
class LoginRequest {
    private String username;
    private String password;
}

@Data
class SignUpRequest {
    private String username;
    private String email;
    private String password;
}

@Data
class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";

    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
