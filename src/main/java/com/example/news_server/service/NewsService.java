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

import java.time.LocalDateTime;
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
                "SELECT created_at, summary1, summary2, summary3, impact, bitcoin_price FROM `%s.%s.summaries` ORDER BY created_at DESC LIMIT %d OFFSET %d",
                PROJECT_ID, DATASET_ID, pageable.getPageSize(), pageable.getOffset()
        );
        return executeSummaryQuery(query, pageable);
    }

    public Page<Translation> getTranslations(Pageable pageable) throws InterruptedException {
        String query = String.format(
                "SELECT created_at, source_url, translated_title, translated_article, original_article, impact_level FROM `%s.%s.translation` ORDER BY created_at DESC LIMIT %d OFFSET %d",
                PROJECT_ID, DATASET_ID, pageable.getPageSize(), pageable.getOffset()
        );
        return executeTranslationQuery(query, pageable);
    }

    private Page<Summary> executeSummaryQuery(String query, Pageable pageable) throws InterruptedException {
        QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).build();
        TableResult result = bigQuery.query(queryConfig);

        List<Summary> summaries = new ArrayList<>();
        result.iterateAll().forEach(row -> {
            LocalDateTime createdAt = LocalDateTime.parse(row.get("created_at").getStringValue(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            summaries.add(new Summary(
                    createdAt,
                    row.get("summary1").getStringValue(),
                    row.get("summary2").getStringValue(),
                    row.get("summary3").getStringValue(),
                    row.get("impact").getStringValue(),
                    row.get("bitcoin_price").getDoubleValue()
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
            LocalDateTime createdAt = LocalDateTime.parse(row.get("created_at").getStringValue(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            translations.add(new Translation(
                    createdAt,
                    row.get("source_url").getStringValue(),
                    row.get("translated_title").getStringValue(),
                    row.get("translated_article").getStringValue(),
                    row.get("original_article").getStringValue(),
                    row.get("impact_level").getStringValue()
            ));
        });
        return new PageImpl<>(translations, pageable, translations.size());
    }
}
