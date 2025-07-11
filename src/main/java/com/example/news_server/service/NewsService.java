package com.example.news_server.service;

import com.example.news_server.model.Summary;
import com.example.news_server.model.Translation;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.TableResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class NewsService {

    private final BigQuery bigQuery;
    private static final String PROJECT_ID = "bitcoinnewscraper";
    private static final String DATASET_ID = "News";

    public NewsService(BigQuery bigQuery) {
        this.bigQuery = bigQuery;
    }

    public Page<Summary> getSummaries(Pageable pageable) throws InterruptedException {
        String query = String.format(
                "SELECT timestamp, summary1_jp, summary2_jp, summary3_jp, impact, bitcoin_price FROM `%s.%s.summaries` ORDER BY timestamp DESC LIMIT %d OFFSET %d",
                PROJECT_ID, DATASET_ID, pageable.getPageSize(), pageable.getOffset()
        );
        return executeSummaryQuery(query, pageable);
    }

    public Page<Translation> getTranslations(Pageable pageable) throws InterruptedException {
        String query = String.format(
                "SELECT timestamp, url, title_jp, summary_jp, impact FROM `%s.%s.translation` ORDER BY timestamp DESC LIMIT %d OFFSET %d",
                PROJECT_ID, DATASET_ID, pageable.getPageSize(), pageable.getOffset()
        );
        return executeTranslationQuery(query, pageable);
    }

    private Page<Summary> executeSummaryQuery(String query, Pageable pageable) throws InterruptedException {
        QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).build();
        TableResult result = bigQuery.query(queryConfig);

        List<Summary> summaries = new ArrayList<>();
        result.iterateAll().forEach(row -> {
            LocalDateTime timestamp = LocalDateTime.ofInstant(Instant.ofEpochMilli(row.get("timestamp").getTimestampValue() / 1000), ZoneOffset.UTC);
            summaries.add(new Summary(
                    timestamp,
                    row.get("summary1_jp").getStringValue(),
                    row.get("summary2_jp").getStringValue(),
                    row.get("summary3_jp").getStringValue(),
                    row.get("impact").getLongValue(),
                    row.get("bitcoin_price").getStringValue()
            ));
        });

        // For simplicity, total elements count is not fetched from BigQuery. 
        // In a real application, you'd run a separate COUNT(*) query.
        return new PageImpl<>(summaries, pageable, summaries.size()); 
    }

    private Page<Translation> executeTranslationQuery(String query, Pageable pageable) throws InterruptedException {
        QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).build();
        TableResult result = bigQuery.query(queryConfig);

        List<Translation> translations = new ArrayList<>();
        result.iterateAll().forEach(row -> {
            LocalDateTime timestamp = LocalDateTime.ofInstant(Instant.ofEpochMilli(row.get("timestamp").getTimestampValue() / 1000), ZoneOffset.UTC);
            translations.add(new Translation(
                    timestamp,
                    row.get("url").getStringValue(),
                    row.get("title_jp").getStringValue(),
                    row.get("summary_jp").getStringValue(),
                    row.get("impact").getLongValue()
            ));
        });
        return new PageImpl<>(translations, pageable, translations.size());
    }
}
