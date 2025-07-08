package com.example.news_server.controller;

import com.example.news_server.model.Summary;
import com.example.news_server.model.Translation;
import com.example.news_server.service.NewsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/summaries")
    public Page<Summary> getSummaries(Pageable pageable) throws InterruptedException {
        return newsService.getSummaries(pageable);
    }

    @GetMapping("/translations")
    public Page<Translation> getTranslations(Pageable pageable) throws InterruptedException {
        return newsService.getTranslations(pageable);
    }
}
