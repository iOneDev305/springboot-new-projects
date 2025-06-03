package com.news.app.web.controller;

import com.news.app.domain.modal.GroupAction;
import com.news.app.domain.repository.GroupActionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/group-actions")
public class GroupActionController {

    @Autowired
    private GroupActionRepository groupActionRepository;

    @GetMapping
    public List<GroupAction> getAllGroupActions() {
        return groupActionRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupAction> getGroupActionById(@PathVariable Long id) {
        Optional<GroupAction> groupAction = groupActionRepository.findById(id);
        return groupAction.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public GroupAction createGroupAction(@RequestParam String actionName) {
        GroupAction groupAction = new GroupAction();
        groupAction.setActionName(actionName);
        return groupActionRepository.save(groupAction);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<GroupAction> updateGroupAction(@PathVariable Long id, @RequestParam String actionName) {
        Optional<GroupAction> groupActionOptional = groupActionRepository.findById(id);

        if (!groupActionOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        GroupAction groupAction = groupActionOptional.get();
        groupAction.setActionName(actionName);

        return ResponseEntity.ok(groupActionRepository.save(groupAction));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroupAction(@PathVariable Long id) {
        Optional<GroupAction> groupAction = groupActionRepository.findById(id);
        return groupAction.map(action -> {
            groupActionRepository.delete(action);
            return ResponseEntity.ok().<Void>build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
} 