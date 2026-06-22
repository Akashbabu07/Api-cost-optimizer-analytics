package com.example.analytics.repository;

import com.example.analytics.entity.ApiMetricEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiMetricRepository extends JpaRepository<ApiMetricEntity, Long> {
}