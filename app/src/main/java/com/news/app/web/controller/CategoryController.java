package com.news.app.web.controller;

import com.news.app.domain.modal.ApiResponse;
import com.news.app.domain.modal.Category;
import com.news.app.domain.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createCategory(@RequestParam("name") String name,
                                          @RequestParam("description") String description) {
        try {
            // Check if name is empty
            if (name == null || name.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error(400, "Category name is required"));
            }

            // Check if category name already exists
            if (categoryRepository.existsByName(name)) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error(400, "Category name '" + name + "' already exists"));
            }

            Category category = new Category();
            category.setName(name);
            category.setDescription(description);

            Category savedCategory = categoryRepository.save(category);
            return ResponseEntity.ok(ApiResponse.success(savedCategory));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(ApiResponse.error(500, "Error creating category: " + e.getMessage()));
        }
    }

    @GetMapping("/index")
    public ResponseEntity<?> getAllCategories() {
        try {
            List<Category> categories = categoryRepository.findAll();
            return ResponseEntity.ok(ApiResponse.success(categories));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(ApiResponse.error(500, "Error fetching categories: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        try {
            return categoryRepository.findById(id)
                    .map(category -> ResponseEntity.ok(ApiResponse.success(category)))
                    .orElse(ResponseEntity.status(404)
                            .body(ApiResponse.error(404, "Category not found with id: " + id)));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(ApiResponse.error(500, "Error fetching category: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id,
                                          @RequestParam("name") String name,
                                          @RequestParam("description") String description) {
        try {
            return categoryRepository.findById(id)
                    .map(existingCategory -> {
                        if (!existingCategory.getName().equals(name) &&
                                categoryRepository.existsByName(name)) {
                            return ResponseEntity.badRequest()
                                    .body(ApiResponse.error(400, "Category name '" + name + "' already exists"));
                        }

                        existingCategory.setName(name);
                        existingCategory.setDescription(description);

                        Category updatedCategory = categoryRepository.save(existingCategory);
                        return ResponseEntity.ok(ApiResponse.success(updatedCategory));
                    })
                    .orElse(ResponseEntity.status(404)
                            .body(ApiResponse.error(404, "Category not found with id: " + id)));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(ApiResponse.error(500, "Error updating category: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        try {
            return categoryRepository.findById(id)
                    .map(category -> {
                        categoryRepository.delete(category);
                        return ResponseEntity.ok(ApiResponse.success("Category deleted successfully"));
                    })
                    .orElse(ResponseEntity.status(404)
                            .body(ApiResponse.error(404, "Category not found with id: " + id)));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(ApiResponse.error(500, "Error deleting category: " + e.getMessage()));
        }
    }
} 