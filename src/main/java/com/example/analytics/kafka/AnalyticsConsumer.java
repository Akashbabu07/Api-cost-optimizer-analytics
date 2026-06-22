package com.example.analytics.kafka;

import com.example.analytics.dto.ApiMetricEvent;
import com.example.analytics.entity.ApiUsageLog;
import com.example.analytics.repository.ApiUsageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnalyticsConsumer {

    private final ApiUsageRepository repository;

    @KafkaListener(topics = "api-metrics-topic", groupId = "analytics-group")
    public void consume(ApiMetricEvent event) {

        if (event == null || event.getApi() == null) return;

        ApiUsageLog log = ApiUsageLog.builder()
                .apiId(event.getApi())
                .endpoint(event.getMethod())
                .latency(event.getLatency())
                .statusCode(event.getStatus())
                .cost(event.getCost())
                .timestamp(event.getTimestamp() != null
                        ? event.getTimestamp()
                        : LocalDateTime.now())
                .build();

        repository.save(log);
    }
}