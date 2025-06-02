package com.news.app.web.dto;

import lombok.Data;

@Data
public class CategoryRequest {
    private String name;
    private String description;
    private String token;
    private Long timestamp;
} 