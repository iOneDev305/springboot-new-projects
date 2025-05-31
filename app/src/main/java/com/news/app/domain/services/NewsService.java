package com.news.app.domain.services;

import java.util.List;
import com.news.app.domain.modal.News;

public interface NewsService {
    List<News> getAllNews();
    List<News> getNewsByCategoryId(Long categoryId);

    void addNews(News news);
}
