package com.news.app.web;

import com.news.app.domain.modal.GroupModuleAction;
import com.news.app.domain.GroupModuleActionRepository;
import com.news.app.domain.PermissionGroupRepository;
import com.news.app.domain.ModuleRepository;
import com.news.app.domain.GroupActionRepository;
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

    @PostMapping
    public ResponseEntity<GroupModuleAction> createGroupModuleAction(@RequestBody GroupModuleAction groupModuleAction) {
        // Basic validation to ensure referenced entities exist
        if (!permissionGroupRepository.existsById(groupModuleAction.getPermissionGroup().getId()) ||
            !moduleRepository.existsById(groupModuleAction.getModule().getId()) ||
            !groupActionRepository.existsById(groupModuleAction.getGroupAction().getId())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(groupModuleActionRepository.save(groupModuleAction));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupModuleAction> updateGroupModuleAction(@PathVariable Long id, @RequestBody GroupModuleAction groupModuleActionDetails) {
        Optional<GroupModuleAction> groupModuleActionOptional = groupModuleActionRepository.findById(id);

        if (!groupModuleActionOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        GroupModuleAction groupModuleAction = groupModuleActionOptional.get();

        // Basic validation for updates as well
        if (groupModuleActionDetails.getPermissionGroup() != null && !permissionGroupRepository.existsById(groupModuleActionDetails.getPermissionGroup().getId()) ||
            groupModuleActionDetails.getModule() != null && !moduleRepository.existsById(groupModuleActionDetails.getModule().getId()) ||
            groupModuleActionDetails.getGroupAction() != null && !groupActionRepository.existsById(groupModuleActionDetails.getGroupAction().getId())) {
            return ResponseEntity.badRequest().build();
        }

        groupModuleAction.setPermissionGroup(groupModuleActionDetails.getPermissionGroup());
        groupModuleAction.setModule(groupModuleActionDetails.getModule());
        groupModuleAction.setGroupAction(groupModuleActionDetails.getGroupAction());

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