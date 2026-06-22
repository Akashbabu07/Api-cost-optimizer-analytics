package com.example.analytics.service;

import com.example.analytics.dto.ApiUsageStats;
import com.example.analytics.dto.DashboardResponse;
import com.example.analytics.entity.ApiUsageLog;
import org.springframework.data.domain.Page;

public interface AnalyticsQueryService {

    Page<ApiUsageLog> getLogs(int page, int size);
    DashboardResponse getSummary();
    ApiUsageStats getApiStats(String apiId);
    void deleteAll();
}