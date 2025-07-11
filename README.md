# News Server API

This Spring Boot application provides API endpoints to query news summaries and translations from Google BigQuery.

## API Endpoints

### 1. Get News Summaries

`GET /api/news/summaries`

Returns a paginated list of news summaries, sorted by `timestamp` in descending order.

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
      "timestamp": "2025-07-08T10:00:00Z",
      "summary1_jp": "要約テキスト1",
      "summary2_jp": "要約テキスト2",
      "summary3_jp": "要約テキスト3",
      "impact": 100,
      "bitcoin_price": "65000.0"
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

Returns a paginated list of news translations, sorted by `timestamp` in descending order.

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
      "timestamp": "2025-07-08T10:00:00Z",
      "url": "https://example.com/news/123",
      "title_jp": "ニュースタイトル日本語",
      "summary_jp": "要約された記事の内容...",
      "impact": 50
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