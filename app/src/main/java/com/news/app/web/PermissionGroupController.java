package com.news.app.web;

import com.news.app.domain.modal.PermissionGroup;
import com.news.app.domain.PermissionGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/permission-groups")
public class PermissionGroupController {

    @Autowired
    private PermissionGroupRepository permissionGroupRepository;

    @GetMapping
    public List<PermissionGroup> getAllPermissionGroups() {
        return permissionGroupRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermissionGroup> getPermissionGroupById(@PathVariable Long id) {
        Optional<PermissionGroup> permissionGroup = permissionGroupRepository.findById(id);
        return permissionGroup.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public PermissionGroup createPermissionGroup(@RequestBody PermissionGroup permissionGroup) {
        return permissionGroupRepository.save(permissionGroup);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PermissionGroup> updatePermissionGroup(@PathVariable Long id, @RequestBody PermissionGroup permissionGroupDetails) {
        Optional<PermissionGroup> permissionGroup = permissionGroupRepository.findById(id);
        return permissionGroup.map(group -> {
            group.setGroupName(permissionGroupDetails.getGroupName());
            group.setActive(permissionGroupDetails.isActive());
            return ResponseEntity.ok(permissionGroupRepository.save(group));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePermissionGroup(@PathVariable Long id) {
        Optional<PermissionGroup> permissionGroup = permissionGroupRepository.findById(id);
        return permissionGroup.map(group -> {
            permissionGroupRepository.delete(group);
            return ResponseEntity.ok().<Void>build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
} 