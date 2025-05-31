package com.news.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class NewsApp {

	public static void main(String[] args) {
		SpringApplication.run(NewsApp.class, args);
	}

	@GetMapping("/news")
	public String getNews() {
		return "Latest news articles will be displayed here.";
	}

}
