# Weather API Wrapper Service

A REST API in Java + Spring Boot that fetches weather data from a third-party provider (OpenWeatherMap), caches responses with expiration, handles errors consistently, and rate-limits requests per client.

roadmap.sh project: [roadmap.sh/projects/weather-api-wrapper-service](https://roadmap.sh/projects/weather-api-wrapper-service)

## Features

- Fetches current weather for a city via [OpenWeatherMap](https://openweathermap.org/api)
- In-memory response caching with expiration (TTL)
- Centralized error handling (e.g. city not found -> `404`)
- Per-IP rate limiting, blocking with `429 Too Many Requests` once the limit is exceeded
- API key kept out of source control, via environment variable

## Tech stack

- Java 17
- Spring Boot 4 (Web, Cache)
- Maven
- JUnit 5 + `MockRestServiceServer` for testing

## Architecture

```
com.pedroMatos.weather_api.demo/
├── controller/   -> exposes HTTP routes (WeatherController)
├── service/      -> business logic: fetching, translation, caching (WeatherService)
├── model/        -> data records (Weather, OpenWeatherResponse)
├── exception/    -> centralized error handling (GlobalExceptionHandler)
├── config/       -> HTTP client configuration (RestClientConfig)
└── filter/       -> per-IP rate limiting (RateLimitingFilter)
```

## Running locally

### Prerequisites

- Java 17+
- A free API key from [OpenWeatherMap](https://home.openweathermap.org/users/sign_up)

### Configuration

Set the API key as an environment variable before running:

```bash
export OPENWEATHER_API_KEY=your_key_here
```

In IntelliJ: **Run -> Edit Configurations -> Environment Variables**, add `OPENWEATHER_API_KEY`.

### Run

```bash
cd demo
./mvnw spring-boot:run
```

The application starts on `http://localhost:8080`.

### Run the tests

```bash
cd demo
./mvnw test
```

## API usage

### Get the weather for a city

```
GET /weather?city={city-name}
```

Example:

```bash
curl "http://localhost:8080/weather?city=Recife"
```

Response:

```json
{
  "city": "Recife",
  "temp": 29.5,
  "weather": "clear sky"
}
```

### Error scenarios

| Scenario | Status | Response |
|---|---|---|
| City not found | `404` | `City not found` |
| Rate limit exceeded | `429` | `Too many request` |

## Project status

Complete - covers every requirement of the original challenge: consuming an external API, TTL caching, environment variables, error handling, and rate limiting.
