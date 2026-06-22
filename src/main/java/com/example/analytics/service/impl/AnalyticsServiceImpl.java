package com.example.analytics.service.impl;

import com.example.analytics.dto.ApiMetricEvent;
import com.example.analytics.dto.ApiUsageContext;
import com.example.analytics.entity.ApiUsageLog;
import com.example.analytics.mapper.AnalyticsMapper;
import com.example.analytics.repository.ApiUsageRepository;
import com.example.analytics.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private static final double DB_HEAVY_LATENCY_THRESHOLD = 300.0;
    private static final long DB_HEAVY_HITS_THRESHOLD = 100L;

    private final ApiUsageRepository repository;
    @Override
    public void saveMetric(ApiMetricEvent event) {
        ApiUsageLog log = AnalyticsMapper.toEntity(event);
        repository.save(log);
    }

    @Override
    public ApiUsageContext buildContext(String apiId) {

        List<ApiUsageLog> logs = repository.findByApiId(apiId);

        if (logs.isEmpty()) {
            return ApiUsageContext.builder()
                    .endpoint(apiId)
                    .hitsPerMinute(0)
                    .avgResponseTime(0)
                    .databaseHeavy(false)
                    .build();
        }
        long hits = logs.size();

        double avgLatency = logs.stream()
                .mapToLong(ApiUsageLog::getLatency)
                .average()
                .orElse(0);

        boolean dbHeavy = avgLatency > DB_HEAVY_LATENCY_THRESHOLD
                || hits > DB_HEAVY_HITS_THRESHOLD;

        return ApiUsageContext.builder()
                .endpoint(apiId)
                .hitsPerMinute(hits)
                .avgResponseTime(avgLatency)
                .databaseHeavy(dbHeavy)
                .build();
    }
}