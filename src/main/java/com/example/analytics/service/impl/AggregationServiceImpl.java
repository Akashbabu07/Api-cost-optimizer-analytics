package com.example.analytics.service.impl;

import com.example.analytics.entity.ApiUsageLog;
import com.example.analytics.entity.DailyMetrics;
import com.example.analytics.entity.HourlyMetrics;
import com.example.analytics.repository.ApiUsageRepository;
import com.example.analytics.repository.DailyMetricsRepository;
import com.example.analytics.repository.HourlyMetricsRepository;
import com.example.analytics.service.AggregationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AggregationServiceImpl implements AggregationService {

    private final ApiUsageRepository apiUsageRepository;

    private final HourlyMetricsRepository hourlyRepository;

    private final DailyMetricsRepository dailyRepository;

    @Override
    public void aggregateHourly() {

        LocalDateTime oneHourAgo =
                LocalDateTime.now().minusHours(24);

        List<ApiUsageLog> logs =
                apiUsageRepository.findLogsAfter(oneHourAgo);

        if (logs.isEmpty()) {
            return;
        }

        long hourBucket =
                LocalDateTime.now()
                        .withMinute(0)
                        .withSecond(0)
                        .withNano(0)
                        .minusHours(1)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
                        .toEpochMilli();

        Map<String, List<ApiUsageLog>> groupedLogs =
                logs.stream()
                        .collect(Collectors.groupingBy(ApiUsageLog::getApiId));

        groupedLogs.forEach((apiId, apiLogs) -> {

            if (hourlyRepository.existsByApiNameAndHourTimestamp(
                    apiId,
                    hourBucket
            )) {
                return;
            }

            long hits = apiLogs.size();

            double avgLatency =
                    apiLogs.stream()
                            .mapToLong(ApiUsageLog::getLatency)
                            .average()
                            .orElse(0);

            double totalCost =
                    apiLogs.stream()
                            .mapToDouble(ApiUsageLog::getCost)
                            .sum();

            HourlyMetrics metrics =
                    HourlyMetrics.builder()
                            .apiName(apiId)
                            .totalHits(hits)
                            .avgResponseTime(avgLatency)
                            .totalCost(totalCost)
                            .hourTimestamp(hourBucket)
                            .build();

            hourlyRepository.save(metrics);
        });
    }

    @Override
    public void aggregateDaily() {

        LocalDate yesterday =
                LocalDate.now().minusDays(1);

        LocalDateTime start =
                yesterday.atStartOfDay();

        LocalDateTime end =
                start.plusDays(1);

        List<ApiUsageLog> logs =
                apiUsageRepository.findLogsBetween(start, end);

        if (logs.isEmpty()) {
            return;
        }

        long dayBucket =
                yesterday
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant()
                        .toEpochMilli();

        Map<String, List<ApiUsageLog>> groupedLogs =
                logs.stream()
                        .collect(Collectors.groupingBy(ApiUsageLog::getApiId));

        groupedLogs.forEach((apiId, apiLogs) -> {

            if (dailyRepository.existsByApiNameAndDayTimestamp(
                    apiId,
                    dayBucket
            )) {
                return;
            }

            long hits = apiLogs.size();

            double avgLatency =
                    apiLogs.stream()
                            .mapToLong(ApiUsageLog::getLatency)
                            .average()
                            .orElse(0);

            double totalCost =
                    apiLogs.stream()
                            .mapToDouble(ApiUsageLog::getCost)
                            .sum();

            DailyMetrics metrics =
                    DailyMetrics.builder()
                            .apiName(apiId)
                            .totalHits(hits)
                            .avgResponseTime(avgLatency)
                            .totalCost(totalCost)
                            .dayTimestamp(dayBucket)
                            .build();

            dailyRepository.save(metrics);
        });
    }
}