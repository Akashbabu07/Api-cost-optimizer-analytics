package com.example.analytics.repository;

import com.example.analytics.entity.ApiUsageLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ApiUsageRepository
        extends JpaRepository<ApiUsageLog, Long> {

    @Query("""
            SELECT a
            FROM ApiUsageLog a
            WHERE a.timestamp >= :time
            """)
    List<ApiUsageLog> findLogsAfter(
            @Param("time") LocalDateTime time
    );

    @Query("""
            SELECT a
            FROM ApiUsageLog a
            WHERE a.timestamp >= :start
            AND a.timestamp < :end
            """)
    List<ApiUsageLog> findLogsBetween(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    long countByApiId(String apiId);

    Page<ApiUsageLog> findByApiId(
            String apiId,
            Pageable pageable
    );

    List<ApiUsageLog> findByApiId(String apiId);
}