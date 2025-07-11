package com.example.news_server.model;

import java.time.LocalDateTime;

public record Summary(
    LocalDateTime timestamp,
    String summary1_jp,
    String summary2_jp,
    String summary3_jp,
    long impact,
    String bitcoin_price
) {}