package com.news.app.domain.repository;

import com.news.app.domain.modal.News;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findByCategoryId(Long categoryId);
    List<News> findByAuthorId(Long authorId);
} 