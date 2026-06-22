package com.example.analytics.repository;

import com.example.analytics.entity.DailyMetrics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyMetricsRepository
        extends JpaRepository<DailyMetrics, Long> {

    boolean existsByApiNameAndDayTimestamp(
            String apiName,
            long dayTimestamp
    );
}