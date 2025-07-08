package com.example.news_server.model;

import java.time.LocalDateTime;

public record Translation(
    LocalDateTime created_at,
    String source_url,
    String translated_title,
    String translated_article,
    String original_article,
    String impact_level
) {}
