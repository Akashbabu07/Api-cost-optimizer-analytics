# Analytics Service

> **The data backbone — consumes API metric events from Kafka, persists them to PostgreSQL, and serves aggregated usage statistics to the Dashboard and Recommendation services.**

---

## Overview

The Analytics Service runs on port `8083` and is the read-optimized core of the platform. It subscribes to the Kafka topic that Metrics publishes to, processes each `ApiMetricEvent`, and stores it in a dedicated `analyticsdb` PostgreSQL database. It then exposes a set of aggregation endpoints that power the Dashboard's summary views and feed context into the Recommendation engine's AI prompts.

---

## Port

`8083`

---

## Responsibilities

- **Kafka consumer** — subscribes to metric events published by the Metrics Service
- **Persistence** — stores usage logs in PostgreSQL (`analyticsdb`)
- **Aggregation** — computes hourly/daily summaries and per-API statistics
- **Context API** — provides usage context payloads consumed by the Recommendation Service

---

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/analytics/logs` | Paginated list of raw usage log entries |
| GET | `/analytics/summary` | Hourly and daily record counts |
| GET | `/analytics/api-stats?apiId=xyz` | Aggregated stats (calls, cost, tokens, latency) for a specific API |
| GET | `/analytics/context?apiId=xyz` | Rich usage context object for AI recommendation prompts |

---

## Database

- **Database:** `analyticsdb` (PostgreSQL)
- **Schema managed by:** Hibernate DDL auto-update
- Stores processed and aggregated API usage logs

---

## Kafka

- **Role:** Consumer
- **Group ID:** `analytics-group`
- **Offset Reset:** `earliest`
- **Deserializer:** `JsonDeserializer` → `ApiMetricEvent`
- Trusted packages: `com.example.analytics.dto`, `com.example.metrics.dto`

---

## Configuration

```yaml
server:
  port: 8083

spring:
  datasource:
    url: ${DB_URL:jdbc:postgresql://localhost:5432/analyticsdb}
    username: ${DB_USERNAME:postgres}
    password: ${DB_PASSWORD}

  kafka:
    bootstrap-servers: ${KAFKA_BOOTSTRAP_SERVERS:localhost:9092}
    consumer:
      group-id: analytics-group
      auto-offset-reset: earliest
```

---

## Environment Variables

| Variable | Description |
|----------|-------------|
| `DB_URL` | PostgreSQL connection URL (default: `jdbc:postgresql://localhost:5432/analyticsdb`) |
| `DB_USERNAME` | Database username |
| `DB_PASSWORD` | Database password |
| `KAFKA_BOOTSTRAP_SERVERS` | Kafka broker address (default: `localhost:9092`) |
| `KAFKA_SECURITY_PROTOCOL` | Kafka security protocol (default: `PLAINTEXT`) |
| `KAFKA_SASL_MECHANISM` | SASL mechanism (for cloud Kafka) |
| `KAFKA_JAAS_CONFIG` | JAAS config string (for cloud Kafka auth) |

---

## Swagger UI

http://localhost:8083/swagger-ui.html

---

## Running

```bash
cd services/analytics
./mvnw spring-boot:run
```
