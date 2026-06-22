package com.example.analytics.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiMetricEvent {

    private String api;
    private String method;
    private int status;
    private long latency;
    private double cost;
    private String userId;
    private LocalDateTime timestamp;
}