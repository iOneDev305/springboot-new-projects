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

    @PostMapping("/create")
    public PermissionGroup createPermissionGroup(@RequestParam String groupName, @RequestParam boolean isActive) {
        PermissionGroup permissionGroup = new PermissionGroup();
        permissionGroup.setGroupName(groupName);
        permissionGroup.setActive(isActive);
        return permissionGroupRepository.save(permissionGroup);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PermissionGroup> updatePermissionGroup(@PathVariable Long id, @RequestParam String groupName, @RequestParam boolean isActive) {
        Optional<PermissionGroup> permissionGroupOptional = permissionGroupRepository.findById(id);

        if (!permissionGroupOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        PermissionGroup permissionGroup = permissionGroupOptional.get();
        permissionGroup.setGroupName(groupName);
        permissionGroup.setActive(isActive);

        return ResponseEntity.ok(permissionGroupRepository.save(permissionGroup));
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