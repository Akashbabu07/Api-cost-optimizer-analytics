package com.example.analytics.scheduler;

import com.example.analytics.service.AggregationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HourlyAggregationJob {

    private final AggregationService aggregationService;

    @Scheduled(cron = "0 0 * * * *")
    public void runHourly() {
        aggregationService.aggregateHourly();
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void runDaily() {
        aggregationService.aggregateDaily();
    }
}