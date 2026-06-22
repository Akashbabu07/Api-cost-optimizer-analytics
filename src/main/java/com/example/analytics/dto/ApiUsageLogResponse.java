package com.example.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiUsageLogResponse {

    private Long id;

    private String apiId;

    private String endpoint;

    private long latency;

    private int statusCode;

    private double cost;

    private LocalDateTime timestamp;
}