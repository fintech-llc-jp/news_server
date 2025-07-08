package com.example.news_server.model;

import java.time.LocalDateTime;

public record Summary(
    LocalDateTime created_at,
    String summary1,
    String summary2,
    String summary3,
    String impact,
    double bitcoin_price
) {}
