package com.example.analytics.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiUsageStats {

    private String apiId;
    private long totalHits;
}