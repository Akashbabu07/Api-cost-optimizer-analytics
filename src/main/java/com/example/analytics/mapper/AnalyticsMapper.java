package com.example.analytics.mapper;

import com.example.analytics.dto.ApiMetricEvent;
import com.example.analytics.entity.ApiUsageLog;

public class AnalyticsMapper {

    public static ApiUsageLog toEntity(ApiMetricEvent dto) {

        ApiUsageLog log = new ApiUsageLog();

        log.setApiId(dto.getApi());
        log.setEndpoint(dto.getMethod());
        log.setLatency(dto.getLatency());
        log.setStatusCode(dto.getStatus());
        log.setCost(dto.getCost());
        log.setTimestamp(dto.getTimestamp());

        return log;
    }
}