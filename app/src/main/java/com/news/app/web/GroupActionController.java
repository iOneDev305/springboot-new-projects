package com.news.app.web;

import com.news.app.domain.modal.GroupAction;
import com.news.app.domain.GroupActionRepository;
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

    @PostMapping
    public GroupAction createGroupAction(@RequestBody GroupAction groupAction) {
        return groupActionRepository.save(groupAction);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupAction> updateGroupAction(@PathVariable Long id, @RequestBody GroupAction groupActionDetails) {
        Optional<GroupAction> groupAction = groupActionRepository.findById(id);
        return groupAction.map(action -> {
            action.setActionName(groupActionDetails.getActionName());
            return ResponseEntity.ok(groupActionRepository.save(action));
        }).orElseGet(() -> ResponseEntity.notFound().build());
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