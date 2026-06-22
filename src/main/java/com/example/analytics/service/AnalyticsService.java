package com.example.analytics.service;

import com.example.analytics.dto.ApiUsageContext;

public interface AnalyticsService {

    void saveMetric(com.example.analytics.dto.ApiMetricEvent event);

    ApiUsageContext buildContext(String apiId);
}