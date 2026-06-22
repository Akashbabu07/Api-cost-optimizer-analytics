package com.example.analytics.repository;

import com.example.analytics.entity.HourlyMetrics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HourlyMetricsRepository
        extends JpaRepository<HourlyMetrics, Long> {

    boolean existsByApiNameAndHourTimestamp(
            String apiName,
            long hourTimestamp
    );
}