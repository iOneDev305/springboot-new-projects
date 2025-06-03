package com.news.app.web.controller;

import com.news.app.domain.modal.ApiResponse;
import com.news.app.domain.modal.User;
import com.news.app.domain.repository.UserRepository;
import com.news.app.infrastructure.security.JwtTokenProvider;
import com.news.app.web.dto.auth.LoginRequest;
import com.news.app.web.dto.auth.SignUpRequest;
import com.news.app.web.dto.auth.JwtAuthenticationResponse;
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

    @PostMapping(value = "/login", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> authenticateUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password) {
        logger.info("Login attempt for user: {}", username);

        try {
            // Check if user exists
            User user = userRepository.findByUsername(username)
                    .orElse(null);

            if (user == null) {
                logger.warn("Login failed: User not found - {}", username);
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error(400, "User not found"));
            }

            logger.debug("Found user: {}", user.getUsername());

            // Verify password
            if (!passwordEncoder.matches(password, user.getPassword())) {
                logger.warn("Login failed: Invalid password for user - {}", username);
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error(400, "Invalid password"));
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            password));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.generateToken(authentication);

            logger.info("Login successful for user: {}", username);
            return ResponseEntity.ok(ApiResponse.success(new JwtAuthenticationResponse(jwt)));
        } catch (BadCredentialsException e) {
            logger.error("Login failed: Bad credentials for user: {} - {}", username, e.getMessage());
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(400, "Invalid username or password"));
        } catch (Exception e) {
            logger.error("Login failed for user: {} - Error: {}", username, e.getMessage());
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(400, "Authentication failed: " + e.getMessage()));
        }
    }

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> registerUser(
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("password") String password) {
        logger.info("Registration attempt for user: {}", username);

        try {
            if (userRepository.existsByUsername(username)) {
                logger.warn("Registration failed: Username already taken - {}", username);
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error(400, "Username is already taken!"));
            }

            if (userRepository.existsByEmail(email)) {
                logger.warn("Registration failed: Email already in use - {}", email);
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error(400, "Email is already in use!"));
            }

            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));

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
    public ResponseEntity<?> getAllUsers(
            @RequestParam(required = false) String token,
            @RequestParam(required = false) String timestamp) {
        logger.info("Fetching all users with token and timestamp");

        try {
            // Check if required parameters exist
            if (token == null) {
                logger.warn("Missing required field: token");
                return ResponseEntity.status(400)
                        .body(ApiResponse.error(400, "Missing required field: token"));
            }

            if (timestamp == null) {
                logger.warn("Missing required field: timestamp");
                return ResponseEntity.status(400)
                        .body(ApiResponse.error(400, "Missing required field: timestamp"));
            }

            // Check if parameters are empty
            if (token.isEmpty()) {
                logger.warn("Token cannot be empty");
                return ResponseEntity.status(400)
                        .body(ApiResponse.error(400, "Token cannot be empty"));
            }

            if (timestamp.isEmpty()) {
                logger.warn("Timestamp cannot be empty");
                return ResponseEntity.status(400)
                        .body(ApiResponse.error(400, "Timestamp cannot be empty"));
            }

            // Validate token
            if (!tokenProvider.validateToken(token)) {
                logger.warn("Invalid or expired token provided");
                return ResponseEntity.status(401)
                        .body(ApiResponse.error(401, "Invalid or expired token. Please login again"));
            }

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
