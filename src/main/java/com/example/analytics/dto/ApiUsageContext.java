package com.example.analytics.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiUsageContext {

    private String endpoint;

    private long hitsPerMinute;

    private double avgResponseTime;

    private boolean databaseHeavy;
}