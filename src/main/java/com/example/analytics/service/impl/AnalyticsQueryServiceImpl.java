package com.example.analytics.service.impl;

import com.example.analytics.dto.ApiUsageStats;
import com.example.analytics.dto.DashboardResponse;
import com.example.analytics.entity.ApiUsageLog;
import com.example.analytics.repository.ApiUsageRepository;
import com.example.analytics.repository.DailyMetricsRepository;
import com.example.analytics.repository.HourlyMetricsRepository;
import com.example.analytics.service.AnalyticsQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnalyticsQueryServiceImpl implements AnalyticsQueryService {

    private final ApiUsageRepository repository;
    private final HourlyMetricsRepository hourlyRepository;
    private final DailyMetricsRepository dailyRepository;
    @Override
    public Page<ApiUsageLog> getLogs(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    @Override
    public DashboardResponse getSummary() {
        return DashboardResponse.builder()
                .totalHourlyRecords((int) hourlyRepository.count())
                .totalDailyRecords((int) dailyRepository.count())
                .build();
    }

    @Override
    public ApiUsageStats getApiStats(String apiId) {
        long totalHits = repository.countByApiId(apiId);
        return ApiUsageStats.builder()
                .apiId(apiId)
                .totalHits(totalHits)
                .build();
    }

    @Override
    public void deleteAll() {
        hourlyRepository.deleteAll();
        dailyRepository.deleteAll();
        repository.deleteAll();
    }
}