package com.news.app.web;

import com.news.app.domain.modal.AdminUser;
import com.news.app.domain.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin-users")
public class AdminUserController {

    @Autowired
    private AdminUserRepository adminUserRepository;

    @GetMapping
    public List<AdminUser> getAllAdminUsers() {
        return adminUserRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminUser> getAdminUserById(@PathVariable Long id) {
        Optional<AdminUser> adminUser = adminUserRepository.findById(id);
        return adminUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public AdminUser createAdminUser(@RequestBody AdminUser adminUser) {
        return adminUserRepository.save(adminUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminUser> updateAdminUser(@PathVariable Long id, @RequestBody AdminUser adminUserDetails) {
        Optional<AdminUser> adminUser = adminUserRepository.findById(id);
        return adminUser.map(user -> {
            user.setUsername(adminUserDetails.getUsername());
            user.setPermissionGroup(adminUserDetails.getPermissionGroup());
            return ResponseEntity.ok(adminUserRepository.save(user));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdminUser(@PathVariable Long id) {
        Optional<AdminUser> adminUser = adminUserRepository.findById(id);
        return adminUser.map(user -> {
            adminUserRepository.delete(user);
            return ResponseEntity.ok().<Void>build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
} 