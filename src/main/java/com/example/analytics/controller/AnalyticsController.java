package com.example.analytics.controller;

import com.example.analytics.dto.ApiUsageContext;
import com.example.analytics.dto.ApiUsageStats;
import com.example.analytics.dto.DashboardResponse;
import com.example.analytics.entity.ApiUsageLog;
import com.example.analytics.service.AggregationService;
import com.example.analytics.service.AnalyticsQueryService;
import com.example.analytics.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsQueryService service;
    private final AnalyticsService analyticsService;
    private final AggregationService aggregationService;

    @GetMapping("/logs")
    public Page<ApiUsageLog> getLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        return service.getLogs(page, size);
    }

    @GetMapping("/summary")
    public DashboardResponse getSummary() {
        return service.getSummary();
    }

    @GetMapping("/api-stats")
    public ApiUsageStats getApiStats(
            @RequestParam String apiId) {

        return service.getApiStats(apiId);
    }

    @GetMapping("/context")
    public ApiUsageContext getContext(@RequestParam String apiId) {
        return analyticsService.buildContext(apiId);
    }

    @PostMapping("/trigger/hourly")
    public String triggerHourly() {
        aggregationService.aggregateHourly();
        return "Hourly aggregation triggered successfully";
    }

    @PostMapping("/trigger/daily")
    public String triggerDaily() {
        aggregationService.aggregateDaily();
        return "Daily aggregation triggered successfully";
    }

    @DeleteMapping("/clear")
    public String clearAll() {
        service.deleteAll();
        return "All analytics data cleared (logs, hourly, daily)";
    }
}