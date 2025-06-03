package com.news.app.web.controller;

import com.news.app.domain.modal.GroupModuleAction;
import com.news.app.domain.modal.PermissionGroup;
import com.news.app.domain.repository.GroupActionRepository;
import com.news.app.domain.repository.GroupModuleActionRepository;
import com.news.app.domain.repository.ModuleRepository;
import com.news.app.domain.repository.PermissionGroupRepository;
import com.news.app.domain.modal.Module;
import com.news.app.domain.modal.GroupAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/group-module-actions")
public class GroupModuleActionController {

    @Autowired
    private GroupModuleActionRepository groupModuleActionRepository;

    @Autowired
    private PermissionGroupRepository permissionGroupRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private GroupActionRepository groupActionRepository;

    @GetMapping
    public List<GroupModuleAction> getAllGroupModuleActions() {
        return groupModuleActionRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupModuleAction> getGroupModuleActionById(@PathVariable Long id) {
        Optional<GroupModuleAction> groupModuleAction = groupModuleActionRepository.findById(id);
        return groupModuleAction.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<GroupModuleAction> createGroupModuleAction(@RequestParam Long groupId, @RequestParam Long moduleId, @RequestParam Long actionId) {
        PermissionGroup permissionGroup = permissionGroupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid permission group ID"));
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid module ID"));
        GroupAction groupAction = groupActionRepository.findById(actionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid action ID"));

        GroupModuleAction groupModuleAction = new GroupModuleAction();
        groupModuleAction.setPermissionGroup(permissionGroup);
        groupModuleAction.setModule(module);
        groupModuleAction.setGroupAction(groupAction);

        return ResponseEntity.ok(groupModuleActionRepository.save(groupModuleAction));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<GroupModuleAction> updateGroupModuleAction(@PathVariable Long id, @RequestParam Long groupId, @RequestParam Long moduleId, @RequestParam Long actionId) {
        Optional<GroupModuleAction> groupModuleActionOptional = groupModuleActionRepository.findById(id);

        if (!groupModuleActionOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        GroupModuleAction groupModuleAction = groupModuleActionOptional.get();

        PermissionGroup permissionGroup = permissionGroupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid permission group ID"));
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid module ID"));
        GroupAction groupAction = groupActionRepository.findById(actionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid action ID"));

        groupModuleAction.setPermissionGroup(permissionGroup);
        groupModuleAction.setModule(module);
        groupModuleAction.setGroupAction(groupAction);

        return ResponseEntity.ok(groupModuleActionRepository.save(groupModuleAction));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroupModuleAction(@PathVariable Long id) {
        Optional<GroupModuleAction> groupModuleAction = groupModuleActionRepository.findById(id);
        return groupModuleAction.map(action -> {
            groupModuleActionRepository.delete(action);
            return ResponseEntity.ok().<Void>build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
} 