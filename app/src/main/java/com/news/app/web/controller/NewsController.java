package com.news.app.web.controller;

import com.news.app.domain.modal.ApiResponse;
import com.news.app.domain.modal.News;
import com.news.app.domain.modal.User;
import com.news.app.domain.repository.NewsRepository;
import com.news.app.domain.repository.UserRepository;
import com.news.app.infrastructure.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> getAllNews() {
        List<News> news = newsRepository.findAll();
        return ResponseEntity.ok(ApiResponse.success(news));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNewsById(@PathVariable Long id) {
        return newsRepository.findById(id)
                .map(news -> ResponseEntity.ok(ApiResponse.success(news)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createNews(@RequestBody News news, @AuthenticationPrincipal UserPrincipal currentUser) {
        User author = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        news.setAuthor(author);
        News savedNews = newsRepository.save(news);
        return ResponseEntity.ok(ApiResponse.success(savedNews));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateNews(@PathVariable Long id, @RequestBody News newsDetails, 
                                      @AuthenticationPrincipal UserPrincipal currentUser) {
        return newsRepository.findById(id)
                .map(existingNews -> {
                    if (!existingNews.getAuthor().getId().equals(currentUser.getId())) {
                        return ResponseEntity.status(403)
                                .body(ApiResponse.error(403, "You don't have permission to update this news"));
                    }

                    existingNews.setTitle(newsDetails.getTitle());
                    existingNews.setContent(newsDetails.getContent());
                    existingNews.setCategory(newsDetails.getCategory());

                    News updatedNews = newsRepository.save(existingNews);
                    return ResponseEntity.ok(ApiResponse.success(updatedNews));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNews(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal currentUser) {
        return newsRepository.findById(id)
                .map(existingNews -> {
                    if (!existingNews.getAuthor().getId().equals(currentUser.getId())) {
                        return ResponseEntity.status(403)
                                .body(ApiResponse.error(403, "You don't have permission to delete this news"));
                    }

                    newsRepository.delete(existingNews);
                    return ResponseEntity.ok(ApiResponse.success("News deleted successfully"));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getNewsByCategory(@PathVariable Long categoryId) {
        List<News> news = newsRepository.findByCategoryId(categoryId);
        return ResponseEntity.ok(ApiResponse.success(news));
    }
} 