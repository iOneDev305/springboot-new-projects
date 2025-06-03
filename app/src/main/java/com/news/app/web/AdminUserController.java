package com.news.app.web;

import com.news.app.domain.modal.AdminUser;
import com.news.app.domain.AdminUserRepository;
import com.news.app.domain.modal.PermissionGroup;
import com.news.app.domain.PermissionGroupRepository;
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

    @Autowired
    private PermissionGroupRepository permissionGroupRepository;

    @GetMapping
    public List<AdminUser> getAllAdminUsers() {
        return adminUserRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminUser> getAdminUserById(@PathVariable Long id) {
        Optional<AdminUser> adminUser = adminUserRepository.findById(id);
        return adminUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public AdminUser createAdminUser(@RequestParam String username, @RequestParam Long permissionGroupId) {
        PermissionGroup permissionGroup = permissionGroupRepository.findById(permissionGroupId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid permission group ID"));
        AdminUser adminUser = new AdminUser();
        adminUser.setUsername(username);
        adminUser.setPermissionGroup(permissionGroup);
        return adminUserRepository.save(adminUser);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AdminUser> updateAdminUser(@PathVariable Long id, @RequestParam String username, @RequestParam Long permissionGroupId) {
        Optional<AdminUser> adminUserOptional = adminUserRepository.findById(id);

        if (!adminUserOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        AdminUser adminUser = adminUserOptional.get();
        PermissionGroup permissionGroup = permissionGroupRepository.findById(permissionGroupId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid permission group ID"));

        adminUser.setUsername(username);
        adminUser.setPermissionGroup(permissionGroup);

        return ResponseEntity.ok(adminUserRepository.save(adminUser));
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