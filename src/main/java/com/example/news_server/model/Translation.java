package com.example.news_server.model;

import java.time.LocalDateTime;

public record Translation(
    LocalDateTime timestamp,
    String url,
    String title_jp,
    String summary_jp,
    long impact
) {}
