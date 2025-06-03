package com.news.app.domain.repository;

import com.news.app.domain.modal.GroupModuleAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupModuleActionRepository extends JpaRepository<GroupModuleAction, Long> {
} 