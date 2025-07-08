# News Server API

This Spring Boot application provides API endpoints to query news summaries and translations from Google BigQuery.

## API Endpoints

### 1. Get News Summaries

`GET /api/news/summaries`

Returns a paginated list of news summaries, sorted by `created_at` in descending order.

#### Query Parameters:

- `page` (optional): The page number to retrieve (0-indexed). Default is `0`.
- `size` (optional): The number of items per page. Default is `10`.

#### Example Request:

```
GET /api/news/summaries?page=0&size=5
```

#### Example Response:

```json
{
  "content": [
    {
      "created_at": "2025-07-08T10:00:00",
      "summary1": "Summary text 1",
      "summary2": "Summary text 2",
      "summary3": "Summary text 3",
      "impact": "High",
      "bitcoin_price": 65000.0
    }
    // ... more summaries
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 5,
    "sort": {
      "empty": false,
      "sorted": true,
      "unsorted": false
    },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "last": false,
  "totalPages": 10,
  "totalElements": 50,
  "size": 5,
  "number": 0,
  "sort": {
    "empty": false,
    "sorted": true,
    "unsorted": false
  },
  "first": true,
  "numberOfElements": 5,
  "empty": false
}
```

### 2. Get News Translations

`GET /api/news/translations`

Returns a paginated list of news translations, sorted by `created_at` in descending order.

#### Query Parameters:

- `page` (optional): The page number to retrieve (0-indexed). Default is `0`.
- `size` (optional): The number of items per page. Default is `10`.

#### Example Request:

```
GET /api/news/translations?page=0&size=5
```

#### Example Response:

```json
{
  "content": [
    {
      "created_at": "2025-07-08T10:00:00",
      "source_url": "https://example.com/news/123",
      "translated_title": "Translated News Title",
      "translated_article": "Translated article content...",
      "original_article": "Original article content...",
      "impact_level": "Medium"
    }
    // ... more translations
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 5,
    "sort": {
      "empty": false,
      "sorted": true,
      "unsorted": false
    },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "last": false,
  "totalPages": 10,
  "totalElements": 50,
  "size": 5,
  "number": 0,
  "sort": {
    "empty": false,
    "sorted": true,
    "unsorted": false
  },
  "first": true,
  "numberOfElements": 5,
  "empty": false
}
```

## Running the Application

To run the application, navigate to the project root directory and execute:

```bash
./gradlew bootRun
```

**Note:** Ensure you have configured your Google Cloud credentials for BigQuery access. Typically, this involves setting the `GOOGLE_APPLICATION_CREDENTIALS` environment variable to the path of your service account key JSON file.
