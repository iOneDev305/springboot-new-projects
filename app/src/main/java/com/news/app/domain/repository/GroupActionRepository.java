package com.news.app.domain.repository;

import com.news.app.domain.modal.GroupAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupActionRepository extends JpaRepository<GroupAction, Long> {
} 