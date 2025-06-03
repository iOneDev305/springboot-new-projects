package com.news.app.domain.modal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "group_module_actions")
public class GroupModuleAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private PermissionGroup permissionGroup;

    @ManyToOne
    @JoinColumn(name = "module_id", nullable = false)
    private Module module;

    @ManyToOne
    @JoinColumn(name = "action_id", nullable = false)
    private GroupAction groupAction;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PermissionGroup getPermissionGroup() {
        return permissionGroup;
    }

    public void setPermissionGroup(PermissionGroup permissionGroup) {
        this.permissionGroup = permissionGroup;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public GroupAction getGroupAction() {
        return groupAction;
    }

    public void setGroupAction(GroupAction groupAction) {
        this.groupAction = groupAction;
    }
} 