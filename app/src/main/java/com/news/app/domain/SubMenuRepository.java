package com.news.app.domain;

import com.news.app.domain.modal.SubMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubMenuRepository extends JpaRepository<SubMenu, Long> {
} 